package com.carciege.api3.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_RESERVATIONS")
public class ReservationModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //user_id

    //car_id

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
