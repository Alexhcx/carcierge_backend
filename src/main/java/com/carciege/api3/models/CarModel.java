package com.carciege.api3.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TB_CARS")
public class CarModel implements Serializable {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<ReservationModel> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationModel> reservations) {
        this.reservations = reservations;
    }

    public Set<RatingModel> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingModel> ratings) {
        this.ratings = ratings;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getTaxa_diaria() {
        return taxa_diaria;
    }

    public void setTaxa_diaria(BigDecimal taxa_diaria) {
        this.taxa_diaria = taxa_diaria;
    }

    public BigDecimal getTaxa_hora() {
        return taxa_hora;
    }

    public void setTaxa_hora(BigDecimal taxa_hora) {
        this.taxa_hora = taxa_hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
