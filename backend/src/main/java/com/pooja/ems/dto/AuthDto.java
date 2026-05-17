package com.pooja.ems.dto;

import com.pooja.ems.model.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// ── Auth DTOs ────────────────────────────────────────

public class AuthDto {

    @Data
    public static class LoginRequest {
        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String username;
        private String role;
        private String fullName;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank private String username;
        @NotBlank @Size(min = 6) private String password;
        @NotBlank private String fullName;
        @Email    private String email;
        private Role role;
    }
}

