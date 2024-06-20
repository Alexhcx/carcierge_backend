package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.CarRecordDto;
import com.carciege.api3.Repositories.CarRepository;
import com.carciege.api3.models.CarModel;
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
import java.util.Set;
import java.util.UUID;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;

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
}
