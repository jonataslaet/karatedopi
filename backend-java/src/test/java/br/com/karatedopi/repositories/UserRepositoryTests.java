package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.UserDetailsProjection;
import br.com.karatedopi.factories.FactoryUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private Long existingId;

    private User newUser;

    @BeforeEach
    public void setUp() {
        existingId = 1L;
        newUser = FactoryUser.newSimpleValidUser();
    }

    @Test
    public void countShouldCountByEmail() {
        Long countBefore = userRepository.countUsersByEmail(newUser.getEmail());
        Assertions.assertEquals(0L, countBefore);
        newUser = userRepository.save(newUser);
        Long newCount = userRepository.countUsersByEmail(newUser.getEmail());
        Assertions.assertEquals(1L, newCount);
        Assertions.assertEquals(countBefore + 1, newCount);
    }

    @Test
    public void saveShouldCreateWhenValidUserDoesNotExist() {
        Long countBefore = userRepository.count();
        Assertions.assertNull(newUser.getId());
        newUser = userRepository.save(newUser);
        Long newCount = userRepository.count();
        Assertions.assertNotNull(newUser.getId());
        Assertions.assertEquals(countBefore + 1, newCount);
    }

    @Test
    public void findShouldFindUserDetailsProjectionWhenEmailExists() {
        newUser = userRepository.save(newUser);
        List<UserDetailsProjection> userDetailsProjections =
                userRepository.searchUserAndRolesByEmail(newUser.getEmail());
        Assertions.assertEquals(userDetailsProjections.size(), 1);
    }

    @Test
    public void findShouldNotFindAnyUserDetailsProjectionWhenEmailDoesNotExist() {
        newUser = userRepository.save(newUser);
        List<UserDetailsProjection> userDetailsProjections =
                userRepository.searchUserAndRolesByEmail("nonexisting"+newUser.getEmail());
        Assertions.assertEquals(userDetailsProjections.size(), 0);
    }

    @Test
    public void findShouldFindAllPagedUsers() {
        newUser = userRepository.save(newUser);
        int pageNumber = 0;
        int pageSize = 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage = userRepository.findAll(pageRequest);
        assertTrue(userPage.hasContent());
        assertEquals(2, userPage.getTotalPages());
        assertEquals(1, userPage.getNumberOfElements());
        assertEquals(2, userPage.getTotalElements());
    }

    @Test
    public void deleteShouldDeleteWhenIdExists() {
        userRepository.deleteById(existingId);
        Optional<User> optionalProduct = userRepository.findById(existingId);
        Assertions.assertTrue(optionalProduct.isEmpty());
    }

    @Test
    public void findByIdShouldFindWhenIdExists() {
        newUser = userRepository.save(newUser);
        User foundUser = userRepository.findById(newUser.getId()).orElse(null);
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(foundUser, newUser);
    }
}
