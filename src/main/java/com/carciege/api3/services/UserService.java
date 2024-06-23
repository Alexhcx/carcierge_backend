package com.carciege.api3.services;

import com.carciege.api3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UUID> getUserIdByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findIdByFirstNameAndLastName(firstName, lastName);
    }
}
