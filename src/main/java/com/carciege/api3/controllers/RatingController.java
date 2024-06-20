package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.RatingRecordDto;
import com.carciege.api3.Repositories.CarRepository;
import com.carciege.api3.Repositories.RatingRepository;
import com.carciege.api3.Repositories.UserRepository;
import com.carciege.api3.models.CarModel;
import com.carciege.api3.models.RatingModel;
import com.carciege.api3.models.UserModel;
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
public class RatingController {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @GetMapping("/ratings")
    public ResponseEntity<List<RatingModel>> getAllRatings(){
        List<RatingModel> ratingsList = ratingRepository.findAll();
        if(!ratingsList.isEmpty()) {
            for(RatingModel rating : ratingsList) {
                UUID id = rating.getId();
                rating.add(linkTo(methodOn(RatingController.class).getOneRating(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ratingsList);
    }

    @GetMapping("/ratings/{id}")
    public ResponseEntity<Object> getOneRating(@PathVariable(value="id") UUID id){
        Optional<RatingModel> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("rating not found.");
        }
        ratingO.get().add(linkTo(methodOn(RatingController.class).getAllRatings()).withRel("Cars List"));
        return ResponseEntity.status(HttpStatus.OK).body(ratingO.get());
    }

    @PostMapping("/ratings")
    public ResponseEntity<RatingModel> saveRating(@RequestBody @Valid RatingRecordDto ratingRecordDto) {
        var ratingModel = new RatingModel();
        BeanUtils.copyProperties(ratingRecordDto, ratingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingRepository.save(ratingModel));
    }

    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Object> deleteRating(@PathVariable(value="id") UUID id) {
        Optional<RatingModel> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found.");
        }
        ratingRepository.delete(ratingO.get());
        return ResponseEntity.status(HttpStatus.OK).body("rating deleted successfully.");
    }

    @PutMapping("/ratings/{id}")
    public ResponseEntity<Object> updateRating(@PathVariable(value="id") UUID id,
                                            @RequestBody @Valid RatingRecordDto ratingRecordDto) {
        Optional<RatingModel> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found.");
        }
        var ratingModel = ratingO.get();
        BeanUtils.copyProperties(ratingRecordDto, ratingModel);
        return ResponseEntity.status(HttpStatus.OK).body(ratingRepository.save(ratingModel));
    }

    @PostMapping("/ratings/fazercomentario")
    public ResponseEntity<Object> saveComentario(@RequestBody @Valid RatingRecordDto ratingRecordDto) {
        Optional<UserModel> userO = userRepository.findById(ratingRecordDto.userId());
        if (userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<CarModel> carO = carRepository.findById(ratingRecordDto.carId());
        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }

        var ratingModel = new RatingModel();
        BeanUtils.copyProperties(ratingRecordDto, ratingModel);
        ratingModel.setUser(userO.get());
        ratingModel.setCar(carO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(ratingRepository.save(ratingModel));
    }

}
