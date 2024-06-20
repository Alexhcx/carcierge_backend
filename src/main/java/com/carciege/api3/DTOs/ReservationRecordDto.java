package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ReservationRecordDto(@NotBlank UUID user,
                                   @NotBlank UUID car,
                                   @NotBlank UUID payment,
                                   @NotBlank String data_reserva,
                                   @NotBlank String data_fim_reserva,
                                   @NotBlank String status_reserva) {
}
