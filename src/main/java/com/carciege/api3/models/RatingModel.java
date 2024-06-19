package com.carciege.api3.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_RATINGS")
public class RatingModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //user_id
    @ManyToOne
    @JoinColumn(name = "user_rating_id")
    private UserModel user;

    //car_id
    @ManyToOne
    @JoinColumn(name = "car_rating_id")
    private CarModel car;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(nullable = false, updatable = false)
    private String created_at;

    @PrePersist
    protected void onCreate() {
        created_at = String.valueOf(LocalDateTime.now());
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
