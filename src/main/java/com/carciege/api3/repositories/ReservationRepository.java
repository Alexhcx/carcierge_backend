package com.carciege.api3.repositories;

import com.carciege.api3.models.Car;
import com.carciege.api3.models.Reservation;
import com.carciege.api3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByUserAndCar(User user, Car car);
}
