package com.carciege.api3.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CARS")
public class CarModel extends RepresentationModel<CarModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<ReservationModel> reservations = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<RatingModel> ratings = new HashSet<>();

    @Column(nullable = false, length = 30)
    private String marca;

    @Column(unique = true, nullable = false, length = 30)
    private String modelo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(length = 500)
    private String imagem;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false, length = 30)
    private String cor;

    @Column(nullable = false, length = 20)
    private String placa;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxa_diaria;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxa_hora;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, updatable = false)
    private String created_at;

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
