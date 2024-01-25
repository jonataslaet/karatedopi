package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.enums.UserRole;
import br.com.karatedopi.factories.FactoryRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    private Long existingRoleRootId;
    private Long existingRoleAdminId;
    private Long existingRoleModeratorId;
    private Long existingRoleUserId;

    private Role roleRoot;
    private Role roleAdmin;
    private Role roleModerator;
    private Role roleUser;

    @BeforeEach
    public void setUp() {
        existingRoleRootId = 1L;
        existingRoleAdminId = 2L;
        existingRoleModeratorId = 3L;
        existingRoleUserId = 4L;
        roleRoot = FactoryRole.roleRoot();
        roleAdmin = FactoryRole.roleAdmin();
        roleModerator = FactoryRole.roleModerator();
        roleUser = FactoryRole.roleUser();
    }

    @Test
    public void updateAllShouldUpdateAllRolesWhenTheyAreValids() {
        Long countBefore = roleRepository.count();
        roleRepository.save(roleRoot);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleModerator);
        roleRepository.save(roleUser);
        Long newCount = roleRepository.count();
        Assertions.assertEquals(countBefore, newCount);
    }

    @ParameterizedTest
    @EnumSource(UserRole.class)
    public void findShouldFindRoleByName(UserRole userRole) {
        String authority = userRole.toString();
        Role foundRole = roleRepository.findByName(authority).orElse(null);
        Assertions.assertNotNull(foundRole);
    }
}
