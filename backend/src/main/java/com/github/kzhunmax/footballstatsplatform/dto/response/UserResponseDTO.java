package com.github.kzhunmax.footballstatsplatform.dto.response;

import com.github.kzhunmax.footballstatsplatform.model.User;

import java.util.Set;

public record UserResponseDTO(String username, String email, Set<String> roles) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRoles());
    }
}

