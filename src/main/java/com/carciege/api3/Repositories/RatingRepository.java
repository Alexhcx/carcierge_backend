package com.carciege.api3.Repositories;

import com.carciege.api3.models.RatingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<RatingModel, UUID> {

    //RatingModel findRatingModelByRating(int rating);

}
