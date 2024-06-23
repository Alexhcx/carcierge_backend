package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;

public record ReservationDto(@NotBlank String data_reserva,
                             @NotBlank String data_fim_reserva,
                             @NotBlank String status_reserva) {
}
