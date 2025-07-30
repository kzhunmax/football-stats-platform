package com.github.kzhunmax.footballstatsplatform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        String confirmPassword
) {
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }
}
