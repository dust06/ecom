package com.ecom.auth.dtos.requestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Native;

public record LoginRequest(
        @NotBlank(message = "username is Mandatory")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        String password) {
}
