package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RatingDto(@NotNull int rating,
                        @NotBlank UUID userId,
                        @NotBlank UUID carId,
                        @NotBlank String comentario) {
}
