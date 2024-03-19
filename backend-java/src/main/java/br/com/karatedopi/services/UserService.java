package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.UserEvaluationDTO;
import br.com.karatedopi.controllers.dtos.UserOutputDTO;
import br.com.karatedopi.entities.UserDetailsProjection;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.entities.enums.Belt;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import br.com.karatedopi.services.exceptions.NoSuchFieldException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final GraduationService graduationService;
    private final ProfileService profileService;
    private final ProfileGraduationService profileGraduationService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<UserDetailsProjection> userDetailsProjections =
                userRepository.searchUserAndRolesByEmail(email);
        if (userDetailsProjections.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado com o email " + email);
        }
        User user = new User();
        user.setId(userDetailsProjections.get(0).getId());
        user.setFirstname(userDetailsProjections.get(0).getFirstname());
        user.setLastname(userDetailsProjections.get(0).getLastname());
        user.setEmail(userDetailsProjections.get(0).getUsername());
        user.setPassword(userDetailsProjections.get(0).getPassword());
        user.setStatus(UserStatus.getValueByValue(userDetailsProjections.get(0).getStatus()));
        for (UserDetailsProjection userDetailsProjection: userDetailsProjections) {
            user.addRole(new Role(userDetailsProjection.getRoleId(), userDetailsProjection.getAuthority()));
        }
        return user;
    }

    @Transactional(readOnly = true)
    public Page<User> getPagedUsers(String search, Pageable pageable) {
        validPageable(pageable);
        return userRepository.findAllBySearchContent(search, pageable);
    }

    @Transactional
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar usuário");
        }
    }

    @Transactional(readOnly = true)
    public Boolean userExistsByEmail(String email) {
        return userRepository.countUsersByEmail(email) > 0;
    }

    @Transactional(readOnly = true)
    public Page<UserOutputDTO> getPagedUsersDTOs(String search, Pageable pageable) {
        return this.getPagedUsers(search, pageable).map(UserOutputDTO::getUserOutputDTO);
    }

    public void loadGraduations(Page<Profile> profiles) {
        Set<ProfileGraduation> profileGraduations = profileGraduationService
                .getProfileGraduationsByProfiles(profiles.stream().map(profile ->
                        profile.getUser().getProfile()).collect(Collectors.toSet()));
        profiles.forEach(profile -> profile.setProfileGraduations(profileGraduations.stream().filter(profileGraduation ->
                profileGraduation.getProfile().equals(profile)).collect(Collectors.toSet())));
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = this.getUser(userId);
        userRepository.deleteById(user.getId());
    }

    private void validPageable(Pageable pageable) {
        Sort sort = pageable.getSort();
        Field[] fields = User.class.getDeclaredFields();
        sort.stream().toList().forEach(order -> {
            final String errorMessage = "O campo "+ order.getProperty() +" não está presente neste usuário";
            Arrays.stream(fields).filter(field -> field.getName().equalsIgnoreCase(order.getProperty()))
                    .findFirst().orElseThrow(() ->  new NoSuchFieldException(errorMessage));

        });
    }

    @Transactional
    public UserOutputDTO evaluateUser(Long id, UserEvaluationDTO userEvaluationDTO) {
        User authenticatedUser = AuthService.authenticated();
        User userToBeEvaluate = getUserWithProfileAndGraduations(id);
        validEvaluate(userToBeEvaluate, authenticatedUser, userEvaluationDTO);
        fillEvaluation(userToBeEvaluate, userEvaluationDTO);
        User updatedUser = this.saveUser(userToBeEvaluate);
        return UserOutputDTO.getUserOutputDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    public User getUserWithProfileAndGraduations(Long id) {
        User foundUser = this.getUser(id);
        loadGraduations(foundUser.getProfile());
        return foundUser;
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o id " + id);
        }
        return foundUser;
    }

    private void validEvaluate(User userToBeEvaluate, User authenticatedUser, UserEvaluationDTO userEvaluationDTO) {
        if (Objects.equals(authenticatedUser.getId(), userToBeEvaluate.getId())) {
            throw new ForbiddenOperationException("O usuário autenticado não pode se avaliar");
        }

        if (!authenticatedUser.isEnabled()) {
            throw new ForbiddenOperationException("O usuário autenticado precisar de estar ativo para avaliar outro usuário");
        }

        Role authenticatedUserBiggerRole = authenticatedUser.getRoles().stream()
                .min(Comparator.comparingLong(Role::getId))
                .orElseThrow(() -> new ResourceStorageException("Problema desconhecido ao carregar autoridade de um usuário"));

        Role userToBeEvaluateBiggerRole = userToBeEvaluate.getRoles().stream()
                .min(Comparator.comparingLong(Role::getId))
                .orElseThrow(() -> new ResourceStorageException("Problema desconhecido ao carregar autoridade de um usuário"));

        Role evaluateRole = roleService.getRoleByName(userEvaluationDTO.authority());

        if (firstRoleHasEqualOrMoreAuthority(userToBeEvaluateBiggerRole, authenticatedUserBiggerRole)) {
            throw new ForbiddenOperationException("Usuário autenticado não pode avaliar outro usuário que tenha autoridade maior ou igual");
        }

        if (firstRoleHasEqualOrMoreAuthority(evaluateRole, authenticatedUserBiggerRole)) {
            throw new ForbiddenOperationException("Usuário autenticado não pode avaliar, com autoridade maior ou igual que a dele próprio, outro usuário");
        }

    }

    private boolean firstRoleHasEqualOrMoreAuthority(Role firstRole, Role secondRole) {
        return firstRole.getId() <= secondRole.getId();
    }

    private void fillEvaluation(User foundUser, UserEvaluationDTO userEvaluationDTO) {
        Role evaluationRole = roleService.getRoleByName(userEvaluationDTO.authority());
        removeBiggerAuthoritiesThanInEvaluationRole(foundUser, evaluationRole);
        foundUser.getRoles().add(evaluationRole);
        foundUser.setStatus(UserStatus.getValueByValue(userEvaluationDTO.status()));
        loadGraduations(foundUser.getProfile());
        profileService.graduateProfile(foundUser.getProfile(), graduationService.getGraduation(Belt.WHITE.toString()));
    }

    private void removeBiggerAuthoritiesThanInEvaluationRole(User foundUser, Role evaluationRole) {
        foundUser.getRoles().removeIf(role -> role.getId() < evaluationRole.getId());
    }

    private void loadGraduations(Profile profile) {
        Set<ProfileGraduation> profileGraduations = profileGraduationService
                .getProfileGraduationsByProfile(profile);
        profile.setProfileGraduations(profileGraduations);
    }
}
