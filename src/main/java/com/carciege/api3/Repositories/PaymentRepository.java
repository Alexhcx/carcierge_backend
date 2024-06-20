package com.carciege.api3.Repositories;

import com.carciege.api3.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {
    //PaymentModel findPaymentModelByValor(PaymentModel valor);
    //PaymentModel findPaymentModelByStatus(String status);
}
