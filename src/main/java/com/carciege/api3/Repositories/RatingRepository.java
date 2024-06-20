package com.carciege.api3.Repositories;

import com.carciege.api3.models.RatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<RatingModel, UUID> {

}
