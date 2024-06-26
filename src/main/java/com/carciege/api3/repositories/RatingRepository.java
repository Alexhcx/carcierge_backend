package com.carciege.api3.repositories;

import com.carciege.api3.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

    List<Rating> findByCarId(UUID carId);
}
