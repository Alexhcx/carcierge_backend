package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RatingRecordDto(@NotNull int rating,
                              @NotBlank UUID user_rating_id,
                              @NotBlank UUID car_rating_id,
                              @NotBlank String comentario) {
}
