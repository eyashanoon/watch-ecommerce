package com.watches.backend.security;

import com.watches.backend.Repositories.UserRepository;
import com.watches.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;



    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("Trying to find user: " + username);
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            System.out.println("User found: " + user);


            return new CustomUserDetails(user);

        } catch (Exception e) {
            System.out.println("❌ Error loading user: " + e.getMessage());
            e.printStackTrace();
            throw e; // Let Spring handle it, so we see the real cause
        }
    }
}
