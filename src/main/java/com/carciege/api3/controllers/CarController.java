package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.CarDto;
import com.carciege.api3.DTOs.ReservationDto;
import com.carciege.api3.repositories.CarRepository;
import com.carciege.api3.repositories.ReservationRepository;
import com.carciege.api3.models.Car;
import com.carciege.api3.models.Reservation;
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
    public ResponseEntity<List<Car>> getAllCars(){
        List<Car> carsList = carRepository.findAll();
        if(!carsList.isEmpty()) {
            for(Car car : carsList) {
                UUID id = car.getId();
                car.add(linkTo(methodOn(CarController.class).getOneCar(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(carsList);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Object> getOneCar(@PathVariable(value="id") UUID id){
        Optional<Car> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("car not found.");
        }
        carO.get().add(linkTo(methodOn(CarController.class).getAllCars()).withRel("Cars List"));
        return ResponseEntity.status(HttpStatus.OK).body(carO.get());
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> saveCar(@RequestBody @Valid CarDto carDto) {
        var carModel = new Car();
        BeanUtils.copyProperties(carDto, carModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(carRepository.save(carModel));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable(value="id") UUID id) {
        Optional<Car> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }
        carRepository.delete(carO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully.");
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CarDto carDto) {
        Optional<Car> carO = carRepository.findById(id);
        if(carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }
        var carModel = carO.get();
        BeanUtils.copyProperties(carDto, carModel);
        return ResponseEntity.status(HttpStatus.OK).body(carRepository.save(carModel));
    }

    @PostMapping("/cars/{id}/reservations")
    public ResponseEntity<Object> saveCarReservation(@PathVariable(value = "id") UUID id,
                                                     @RequestBody @Valid ReservationDto reservationDto) {
        Optional<Car> carO = carRepository.findById(id);
        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }

        var reservationModel = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservationModel);
        reservationModel.setCar(carO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

}
