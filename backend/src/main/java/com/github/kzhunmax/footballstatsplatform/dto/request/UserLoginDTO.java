package com.github.kzhunmax.footballstatsplatform.dto.request;

public record UserLoginDTO(
        String usernameOrEmail,
        String password
) {
}
