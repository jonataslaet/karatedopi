package br.com.karatedopi.services;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.RoleRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleService {
    private static final Long ROLE_USER_ID = 4L;

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        Role foundUser = roleRepository.findByName(name.toUpperCase()).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("Role not found for name = " + name);
        }
        return foundUser;
    }

    @Transactional(readOnly = true)
    public Role getRoleUser() {
        Role foundUser = roleRepository.findById(ROLE_USER_ID).orElse(null);
        if (Objects.isNull(foundUser)) {
            throw new ResourceNotFoundException("Role not found for id = " + ROLE_USER_ID);
        }
        return foundUser;
    }

}
