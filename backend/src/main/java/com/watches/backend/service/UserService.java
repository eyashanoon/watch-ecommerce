package com.watches.backend.service;

import com.watches.backend.Repositories.UserRepository;
import com.watches.backend.enums.Role;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkRoles(String username, List<String> roles){

        if(Utils.isNullOrWhiteSpace(username) || Utils.isNullOrEmpty(roles)){
            return false;
        }

        User user = findByUsername(username);

        for(String role : roles){
            if(role == null) return false;
            try {
                Role ro = Role.valueOf(role);
                if (!user.getRoles().contains(ro)) {
                    return false;
                }
            }catch (IllegalArgumentException ex){
                return false;
            }
        }
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> CException.notFound(User.class, "email", username));
    }

}
