package com.github.kzhunmax.footballstatsplatform.service;

import com.github.kzhunmax.footballstatsplatform.dto.request.UserRegistrationDTO;
import com.github.kzhunmax.footballstatsplatform.dto.response.UserResponseDTO;
import com.github.kzhunmax.footballstatsplatform.model.User;
import com.github.kzhunmax.footballstatsplatform.repository.UserRepository;
import com.github.kzhunmax.footballstatsplatform.validator.UserRegistrationValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRegistrationValidator userRegistrationValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO  dto) {
        userRegistrationValidator.validateRegistration(dto);

        User user = User.builder()
                .username(dto.username().trim())
                .email(dto.email().trim())
                .password(passwordEncoder.encode(dto.password()))
                .roles(Set.of("ROLE_USER"))
                .build();

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }
}
