package br.com.karatedopi.services;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.enums.UserRole;
import br.com.karatedopi.repositories.RoleRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        Role foundUser = roleRepository.findByName(name.toUpperCase()).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("Role not found for name = " + name);
        }
        return foundUser;
    }

    public Role getRoleUser() {
        final Long roleUserId = UserRole.ROLE_USER.getId().longValue();
        Role foundUser = roleRepository.findById(roleUserId).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("Role not found for id = " + roleUserId);
        }
        return foundUser;
    }

}
