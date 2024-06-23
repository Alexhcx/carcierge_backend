package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.ReservationDto;
import com.carciege.api3.DTOs.UserDto;
import com.carciege.api3.repositories.ReservationRepository;
import com.carciege.api3.repositories.UserRepository;
import com.carciege.api3.models.Reservation;
import com.carciege.api3.models.User;
import com.carciege.api3.services.UserService;
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
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> usersList = userRepository.findAll();
        if(!usersList.isEmpty()) {
            for(User user : usersList) {
                UUID id = user.getId();
                user.add(linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value="id") UUID id){
        Optional<User> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found.");
        }
        userO.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("Users List"));
        return ResponseEntity.status(HttpStatus.OK).body(userO.get());
    }

    @GetMapping("/user/id")
    public ResponseEntity<UUID> getUserId(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<UUID> userId = userService.getUserIdByFirstNameAndLastName(firstName, lastName);
        return userId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") UUID id) {
        Optional<User> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userRepository.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid UserDto userDto) {
        Optional<User> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var userModel = userO.get();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @PostMapping("/users/{id}/reservations")
    public ResponseEntity<Object> saveUserReservation(@PathVariable(value = "id") UUID id,
                                                      @RequestBody @Valid ReservationDto reservationDto) {
        Optional<User> userO = userRepository.findById(id);
        if (userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        var reservationModel = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservationModel);
        reservationModel.setUser(userO.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationRepository.save(reservationModel));
    }

}
