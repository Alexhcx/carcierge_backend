package com.carciege.api3.controllers;


import com.carciege.api3.DTOs.FazerReservaDto;
import com.carciege.api3.DTOs.ReservationDto;

import com.carciege.api3.repositories.CarRepository;
import com.carciege.api3.repositories.ReservationRepository;
import com.carciege.api3.repositories.UserRepository;
import com.carciege.api3.models.Car;
import com.carciege.api3.models.Reservation;

import com.carciege.api3.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservationsList = reservationRepository.findAll();
        if(!reservationsList.isEmpty()) {
            for(Reservation reservation : reservationsList) {
                UUID id = reservation.getId();
                reservation.add(linkTo(methodOn(ReservationController.class).getOneReservation(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservationsList);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Object> getOneReservation(@PathVariable(value="id") UUID id){
        Optional<Reservation> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reservation not found.");
        }
        reservationO.get().add(linkTo(methodOn(ReservationController.class).getAllReservations()).withRel("Reservations List"));
        return ResponseEntity.status(HttpStatus.OK).body(reservationO.get());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> saveReservation(@RequestBody @Valid ReservationDto reservationDto) {
        var reservationModel = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservationModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable(value="id") UUID id) {
        Optional<Reservation> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
        reservationRepository.delete(reservationO.get());
        return ResponseEntity.status(HttpStatus.OK).body("reservation deleted successfully.");
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid ReservationDto reservationDto) {
        Optional<Reservation> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
        var reservationModel = reservationO.get();
        BeanUtils.copyProperties(reservationDto, reservationModel);
        return ResponseEntity.status(HttpStatus.OK).body(reservationRepository.save(reservationModel));
    }

    @PostMapping("/reservations/addreserva")
    public ResponseEntity<Object> createCompleteReservation(@RequestBody @Valid FazerReservaDto completeReservationDto) {
        Optional<User> userO = userRepository.findById(completeReservationDto.userId());
        if (userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<Car> carO = carRepository.findById(completeReservationDto.carId());
        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }

        var reservationModel = new Reservation();
        BeanUtils.copyProperties(completeReservationDto, reservationModel);
        reservationModel.setUser(userO.get());
        reservationModel.setCar(carO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

    @GetMapping("/reservations/user/{userId}/car/{carId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserAndCar(
            @PathVariable UUID userId,
            @PathVariable UUID carId
    ) {
        Optional<User> userO = userRepository.findById(userId);
        Optional<Car> carO = carRepository.findById(carId);

        if (userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Reservation> reservationsList = reservationRepository.findByUserAndCar(userO.get(), carO.get());

        if (reservationsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Adicionando links HATEOAS para cada reserva
        for (Reservation reservation : reservationsList) {
            UUID id = reservation.getId();
            reservation.add(linkTo(methodOn(ReservationController.class).getOneReservation(id)).withSelfRel());
        }

        return ResponseEntity.status(HttpStatus.OK).body(reservationsList);
    }

}
