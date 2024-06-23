package com.carciege.api3.repositories;

import com.carciege.api3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String username);

    @Query("SELECT u.id FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    Optional<UUID> findIdByFirstNameAndLastName(String firstName, String lastName);
}
