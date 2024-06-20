package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRecordDto(@NotNull BigDecimal valor,
                               @NotBlank String data_pagamento,
                               @NotBlank String metodo_pagamento,
                               @NotBlank String status) {
}
