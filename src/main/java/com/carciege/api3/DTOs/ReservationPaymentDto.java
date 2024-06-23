package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ReservationPaymentDto(UUID reservationId,
                                    @NotBlank String status,
                                    @NotBlank String metodo_pagamento,
                                    @NotNull BigDecimal valor) {
}
