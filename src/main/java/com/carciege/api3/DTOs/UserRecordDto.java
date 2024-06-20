package com.carciege.api3.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String firstName,
                            @NotBlank String lastName,
                            @NotBlank String email,
                            @NotBlank String password,
                            String phone_number,
                            String city,
                            String state,
                            String address) {
}
