package com.carciege.api3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_RESERVATIONS")
public class ReservationModel extends RepresentationModel<ReservationModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //user_id
    @ManyToOne
    @JoinColumn(name = "user_reserv_id")
    private UserModel user;

    //car_id
    @ManyToOne
    @JoinColumn(name = "car_reserv_id")
    private CarModel car;

    @OneToOne (mappedBy = "reservation", cascade = CascadeType.ALL)
    private PaymentModel payment;

    @Column(nullable = false)
    private String data_reserva;

    @Column(nullable = false)
    private String data_fim_reserva;

    @Column(nullable = false)
    private String status_reserva;

    @Column(nullable = false, updatable = false)
    private String created_at;

    @Column(nullable = false)
    private String updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = String.valueOf(LocalDateTime.now());
        updated_at = String.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = String.valueOf(LocalDateTime.now());
    }
}
