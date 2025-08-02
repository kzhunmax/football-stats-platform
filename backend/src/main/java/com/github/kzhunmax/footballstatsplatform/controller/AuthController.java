package com.github.kzhunmax.footballstatsplatform.controller;

import com.github.kzhunmax.footballstatsplatform.dto.request.UserLoginDTO;
import com.github.kzhunmax.footballstatsplatform.dto.request.UserRegistrationDTO;
import com.github.kzhunmax.footballstatsplatform.dto.response.JwtResponse;
import com.github.kzhunmax.footballstatsplatform.dto.response.UserResponseDTO;
import com.github.kzhunmax.footballstatsplatform.payload.ApiResponse;
import com.github.kzhunmax.footballstatsplatform.security.JwtService;
import com.github.kzhunmax.footballstatsplatform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {

        String requestId = UUID.randomUUID().toString();
        log.info("Processing registration request | requestId={}, username={}", requestId, userRegistrationDTO.username());

        UserResponseDTO user = authService.registerUser(userRegistrationDTO);
        log.info("Successfully registered user | requestId={} username={}", requestId, user.username());
        return ApiResponse.created(user, requestId);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody UserLoginDTO loginDto) {
        String requestId = UUID.randomUUID().toString();
        log.info("Login attempt | requestId={}, username={}", requestId, loginDto.usernameOrEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.usernameOrEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        log.info("Successful login | requestId={}, username={}", requestId, userDetails.getUsername());
        return ApiResponse.success(new JwtResponse(jwtToken), requestId);
    }
}
