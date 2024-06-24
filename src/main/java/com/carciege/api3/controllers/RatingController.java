package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.GetRatingInfo;
import com.carciege.api3.DTOs.RatingDto;
import com.carciege.api3.mapper.RatingMapper;
import com.carciege.api3.repositories.CarRepository;
import com.carciege.api3.repositories.RatingRepository;
import com.carciege.api3.repositories.UserRepository;
import com.carciege.api3.models.Car;
import com.carciege.api3.models.Rating;
import com.carciege.api3.models.User;
import com.carciege.api3.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private RatingService ratingService;

    @GetMapping("/ratings")
    public ResponseEntity<List<Rating>> getAllRatings(){
        List<Rating> ratingsList = ratingRepository.findAll();
        if(!ratingsList.isEmpty()) {
            for(Rating rating : ratingsList) {
                UUID id = rating.getId();
                rating.add(linkTo(methodOn(RatingController.class).getOneRating(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ratingsList);
    }

    @GetMapping("/ratings/{id}")
    public ResponseEntity<Object> getOneRating(@PathVariable(value="id") UUID id){
        Optional<Rating> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("rating not found.");
        }
        ratingO.get().add(linkTo(methodOn(RatingController.class).getAllRatings()).withRel("Cars List"));
        return ResponseEntity.status(HttpStatus.OK).body(ratingO.get());
    }

    @GetMapping("/ratings/all")
    public List<GetRatingInfo> getAllRatingsAll() {
        List<Rating> ratings = ratingService.findAll();
        return ratings.stream()
                .map(RatingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ratings/car/{carId}")
    public ResponseEntity<Object> getRatingsByCarId(@PathVariable UUID carId) {
        List<Rating> ratings = ratingRepository.findByCarId(carId);
        if (ratings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No ratings found for car with ID: " + carId);
        }
        List<GetRatingInfo> ratingInfoList = ratings.stream()
                .map(RatingMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ratingInfoList);
    }

    @PostMapping("/ratings")
    public ResponseEntity<Rating> saveRating(@RequestBody @Valid RatingDto ratingDto) {
        var ratingModel = new Rating();
        BeanUtils.copyProperties(ratingDto, ratingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingRepository.save(ratingModel));
    }

    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Object> deleteRating(@PathVariable(value="id") UUID id) {
        Optional<Rating> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found.");
        }
        ratingRepository.delete(ratingO.get());
        return ResponseEntity.status(HttpStatus.OK).body("rating deleted successfully.");
    }

    @PutMapping("/ratings/{id}")
    public ResponseEntity<Object> updateRating(@PathVariable(value="id") UUID id,
                                            @RequestBody @Valid RatingDto ratingDto) {
        Optional<Rating> ratingO = ratingRepository.findById(id);
        if(ratingO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found.");
        }
        var ratingModel = ratingO.get();
        BeanUtils.copyProperties(ratingDto, ratingModel);
        return ResponseEntity.status(HttpStatus.OK).body(ratingRepository.save(ratingModel));
    }

    @PostMapping("/ratings/fazercomentario")
    public ResponseEntity<Object> saveComentario(@RequestBody @Valid RatingDto ratingDto) {
        Optional<User> userO = userRepository.findById(ratingDto.userId()); // Agora usa ratingDto.userId()
        if (userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<Car> carO = carRepository.findById(ratingDto.carId());
        if (carO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
        }

        var ratingModel = new Rating();
        BeanUtils.copyProperties(ratingDto, ratingModel);
        ratingModel.setUser(userO.get());
        ratingModel.setCar(carO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(ratingRepository.save(ratingModel));
    }
}
