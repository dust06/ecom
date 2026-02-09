package com.ecom.authservice.auth.dtos.responseDTOs;

import jakarta.validation.constraints.NotBlank;

public record SignUpResponseDTO(
        @NotBlank
        String token
) {
}
