package com.carciege.api3.DTOs;

import com.carciege.api3.models.Payment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
public class PaymentReservationDTO {
    private UUID id;
    private UUID reservation_id;
    private BigDecimal valor;
    private String data_pagamento;
    private String metodo_pagamento;
    private String status;
    private String created_at;
    private String updated_at;

    public PaymentReservationDTO(Payment payment) {
        this.id = payment.getId();
        this.reservation_id = payment.getReservation().getId();
        this.valor = payment.getValor();
        this.data_pagamento = payment.getData_pagamento();
        this.metodo_pagamento = payment.getMetodo_pagamento();
        this.status = payment.getStatus();
        this.created_at = payment.getCreated_at();
        this.updated_at = payment.getUpdated_at();
    }
}
