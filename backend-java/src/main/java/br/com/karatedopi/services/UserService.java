package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.UserReadDTO;
import br.com.karatedopi.controllers.dtos.UserEvaluationDTO;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.UserDetailsProjection;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.NoSuchFieldException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<UserDetailsProjection> userDetailsProjections =
                userRepository.searchUserAndRolesByEmail(email);
        if (userDetailsProjections.isEmpty()) {
            throw new ResourceNotFoundException("User not found for email = " + email);
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

    public Page<UserReadDTO> getPagedUsers(Pageable pageable) {
        validPageable(pageable);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserReadDTO::getUserReadDTO);
    }

    private void validPageable(Pageable pageable) {
        Sort sort = pageable.getSort();
        Field[] fields = User.class.getDeclaredFields();
        sort.stream().toList().forEach(order -> {
            final String errorMessage = "The field "+ order.getProperty() +" is not present in the user";
            Arrays.stream(fields).filter(field -> field.getName().equalsIgnoreCase(order.getProperty()))
                .findFirst().orElseThrow(() ->  new NoSuchFieldException(errorMessage));

        });
    }

    @Transactional
    public UserReadDTO evaluateUser(Long id, UserEvaluationDTO userEvaluationDTO) {
        User foundUser = getUser(id);
        fillEvaluation(foundUser, userEvaluationDTO);
        User updatedUser = this.saveUser(foundUser);
        return UserReadDTO.getUserReadDTO(updatedUser);
    }

    private void fillEvaluation(User foundUser, UserEvaluationDTO userEvaluationDTO) {
        Role evaluationRole = roleService.getRoleByName(userEvaluationDTO.authority());
        removeBiggerAuthoritiesThanInEvaluationRole(foundUser, evaluationRole);
        foundUser.getRoles().add(evaluationRole);
        foundUser.setStatus(UserStatus.getValueByName(userEvaluationDTO.status()));
    }

    private void removeBiggerAuthoritiesThanInEvaluationRole(User foundUser, Role evaluationRole) {
        Iterator<Role> iterator = foundUser.getRoles().iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getId() < evaluationRole.getId()) {
                iterator.remove();
            }
        }
    }

    @Transactional(readOnly = true)
    private User getUser(Long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("User not found id = " + id);
        }
        return foundUser;
    }

    @Transactional
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceStorageException("Unknown problem by saving user");
        }
    }

    public Boolean userExistsByEmail(String email) {
        return userRepository.countUsersByEmail(email) > 0;
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = this.getUser(userId);
        userRepository.deleteById(user.getId());
    }
}
