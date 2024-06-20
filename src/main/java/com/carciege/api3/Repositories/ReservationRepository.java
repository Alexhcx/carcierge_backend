package com.carciege.api3.Repositories;

import com.carciege.api3.models.RatingModel;
import com.carciege.api3.models.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, UUID> {

}
