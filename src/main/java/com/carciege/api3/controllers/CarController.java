package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.CarRecordDto;
import com.carciege.api3.DTOs.ReservationRecordDto;
import com.carciege.api3.repositories.CarRepository;
import com.carciege.api3.repositories.ReservationRepository;
import com.carciege.api3.models.CarModel;
import com.carciege.api3.models.ReservationModel;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/cars")
    public ResponseEntity<List<CarModel>> getAllCars(){
        List<CarModel> carsList = carRepository.findAll();
        if(!carsList.isEmpty()) {
            for(CarModel car : carsList) {
                UUID id = car.getId();
                car.add(linkTo(methodOn(CarController.class).getOneCar(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(carsList);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Object> getOneCar(@PathVariable(value="id") UUID id){
        Optional<CarModel> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("car not found.");
        }
        carO.get().add(linkTo(methodOn(CarController.class).getAllCars()).withRel("Cars List"));
        return ResponseEntity.status(HttpStatus.OK).body(carO.get());
    }

    @PostMapping("/cars")
    public ResponseEntity<CarModel> saveCar(@RequestBody @Valid CarRecordDto carRecordDto) {
        var carModel = new CarModel();
        BeanUtils.copyProperties(carRecordDto, carModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(carRepository.save(carModel));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable(value="id") UUID id) {
        Optional<CarModel> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }
        carRepository.delete(carO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully.");
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CarRecordDto carRecordDto) {
        Optional<CarModel> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }
        var carModel = carO.get();
        BeanUtils.copyProperties(carRecordDto, carModel);
        return ResponseEntity.status(HttpStatus.OK).body(carRepository.save(carModel));
    }

    @PostMapping("/cars/{id}/reservations")
    public ResponseEntity<Object> saveCarReservation(@PathVariable(value = "id") UUID id,
                                                     @RequestBody @Valid ReservationRecordDto reservationRecordDto) {
        Optional<CarModel> carO = carRepository.findById(id);
        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }

        var reservationModel = new ReservationModel();
        BeanUtils.copyProperties(reservationRecordDto, reservationModel);
        reservationModel.setCar(carO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

}
