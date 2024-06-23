package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.LoginRequestDTO;
import com.carciege.api3.DTOs.RegisterRequestDTO;
import com.carciege.api3.DTOs.ResponseDTO;
import com.carciege.api3.infra.security.TokenService;
import com.carciege.api3.models.User;
import com.carciege.api3.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getFirstName(), user.getLastName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setFirstName(body.firstName());
            newUser.setLastName(body.lastName());
            newUser.setPhone_number(body.phone_number());
            newUser.setCity(body.city());
            newUser.setState(body.state());
            newUser.setAddress(body.address());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getFirstName(),newUser.getLastName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
