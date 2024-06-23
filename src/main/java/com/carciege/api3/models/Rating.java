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
@Table(name = "TB_RATINGS")
public class Rating extends RepresentationModel<Rating> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //user_id
    @ManyToOne
    @JoinColumn(name = "user_rating_id")
    private User user;

    //car_id
    @ManyToOne
    @JoinColumn(name = "car_rating_id")
    private Car car;

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
}
