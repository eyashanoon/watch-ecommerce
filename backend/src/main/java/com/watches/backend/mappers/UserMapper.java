package com.watches.backend.mappers;

import com.watches.backend.model.User;
import com.watches.backend.Dto.UserDTO;

public class UserMapper {

    // Convert Entity to DTO
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    // Convert DTO to Entity (for creation) — optional or throw for abstract class
    public static User fromDTO(UserDTO dto) {
        throw new UnsupportedOperationException("Use CustomerMapper or AdminMapper to convert DTO to entity.");
    }
}
