package com.carciege.api3.Repositories;

import com.carciege.api3.models.RatingModel;
import com.carciege.api3.models.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationModel, UUID> {

    //ReservationModel findReservationModelByData_reserva(String data_reserva);
    //ReservationModel findReservationModelByStatus_reserva(String status_reserva);

}
