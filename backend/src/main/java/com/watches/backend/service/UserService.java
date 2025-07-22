package com.watches.backend.service;

import com.watches.backend.model.User;
import com.watches.backend.Dto.UserDTO;
import com.watches.backend.mappers.UserMapper;
import com.watches.backend.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO createUser(UserDTO userDTO) {
        // Not supported since User is abstract
        throw new UnsupportedOperationException("Cannot create abstract User directly. Use CustomerService or AdminService.");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
