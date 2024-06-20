package com.carciege.api3.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
    }

    public String getData_reserva() {
        return data_reserva;
    }

    public void setData_reserva(String data_reserva) {
        this.data_reserva = data_reserva;
    }

    public String getData_fim_reserva() {
        return data_fim_reserva;
    }

    public void setData_fim_reserva(String data_fim_reserva) {
        this.data_fim_reserva = data_fim_reserva;
    }

    public String getStatus_reserva() {
        return status_reserva;
    }

    public void setStatus_reserva(String status_reserva) {
        this.status_reserva = status_reserva;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
