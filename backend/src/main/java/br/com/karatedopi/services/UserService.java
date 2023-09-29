package br.com.karatedopi.services;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.UserDetailsProjection;
import br.com.karatedopi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<UserDetailsProjection> userDetailsProjections =
                userRepository.searchUserAndRolesByEmail(email);
        if (userDetailsProjections.isEmpty()) {
            throw new UsernameNotFoundException("User not found for email = " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(userDetailsProjections.get(0).getPassword());
        for (UserDetailsProjection userDetailsProjection :
                userDetailsProjections) {
            user.addRole(new Role(userDetailsProjection.getRoleId(), userDetailsProjection.getAuthority()));
        }
        return user;
    }
}
