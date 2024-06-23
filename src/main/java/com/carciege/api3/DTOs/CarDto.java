package com.carciege.api3.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarDto(@NotBlank String marca,
                     @NotBlank String modelo,
                     @NotBlank String descricao,
                     @NotBlank String imagem,
                     @NotNull int ano,
                     @NotBlank String cor,
                     @NotBlank String placa,
                     @NotNull BigDecimal taxa_diaria,
                     @NotNull BigDecimal taxa_hora,
                     @NotBlank String status) {
}
