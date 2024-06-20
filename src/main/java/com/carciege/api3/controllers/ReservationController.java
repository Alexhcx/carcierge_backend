package com.carciege.api3.controllers;


import com.carciege.api3.DTOs.ReservationRecordDto;

import com.carciege.api3.Repositories.ReservationRepository;
import com.carciege.api3.models.ReservationModel;

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

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationModel>> getAllReservations(){
        List<ReservationModel> reservationsList = reservationRepository.findAll();
        if(!reservationsList.isEmpty()) {
            for(ReservationModel reservation : reservationsList) {
                UUID id = reservation.getId();
                reservation.add(linkTo(methodOn(ReservationController.class).getOneReservation(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservationsList);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Object> getOneReservation(@PathVariable(value="id") UUID id){
        Optional<ReservationModel> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reservation not found.");
        }
        reservationO.get().add(linkTo(methodOn(ReservationController.class).getAllReservations()).withRel("Reservations List"));
        return ResponseEntity.status(HttpStatus.OK).body(reservationO.get());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationModel> saveReservation(@RequestBody @Valid ReservationRecordDto reservationRecordDto) {
        var reservationModel = new ReservationModel();
        BeanUtils.copyProperties(reservationRecordDto, reservationModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable(value="id") UUID id) {
        Optional<ReservationModel> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
        reservationRepository.delete(reservationO.get());
        return ResponseEntity.status(HttpStatus.OK).body("reservation deleted successfully.");
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid ReservationRecordDto reservationRecordDto) {
        Optional<ReservationModel> reservationO = reservationRepository.findById(id);
        if(reservationO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
        var reservationModel = reservationO.get();
        BeanUtils.copyProperties(reservationRecordDto, reservationModel);
        return ResponseEntity.status(HttpStatus.OK).body(reservationRepository.save(reservationModel));
    }
}
