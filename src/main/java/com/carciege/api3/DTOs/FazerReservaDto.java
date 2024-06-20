package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FazerReservaDto(@NotBlank String data_reserva,
                              @NotBlank String data_fim_reserva,
                              @NotBlank String status_reserva,
                              @NotNull UUID userId,
                              @NotNull UUID carId) {
}