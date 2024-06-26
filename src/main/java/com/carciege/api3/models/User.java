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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
public class User extends RepresentationModel<User> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone_number;

    private String city;

    private String state;

    private String address;

    @Column(nullable = false, updatable = false)
    private String created_at;

    private String updated_at;

    public User(UUID id, String firstName, String lastName) {
    }

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
