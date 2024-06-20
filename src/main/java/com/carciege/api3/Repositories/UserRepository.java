package com.carciege.api3.Repositories;

import com.carciege.api3.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    //UserModel findUserModelByFirstName(String firstName);
    //UserModel findUserModelByLastName(String lastName);
    //UserModel findUserModelByCity(String city);
    //UserModel findUserModelByState(String state);
}
