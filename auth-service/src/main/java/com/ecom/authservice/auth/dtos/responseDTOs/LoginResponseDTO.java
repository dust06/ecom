package com.ecom.authservice.auth.dtos.responseDTOs;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(
        @NotBlank
        String token
) {
}
