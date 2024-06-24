package com.carciege.api3.mapper;

import com.carciege.api3.DTOs.rating.CarRatingDto;
import com.carciege.api3.DTOs.rating.UserRatingDto;
import com.carciege.api3.DTOs.GetRatingInfo;
import com.carciege.api3.models.Rating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RatingMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static GetRatingInfo toDTO(Rating rating) {
        return new GetRatingInfo(
                rating.getId(),
                new UserRatingDto(
                        rating.getUser().getFirstName(),
                        rating.getUser().getLastName(),
                        rating.getUser().getId().toString()
                ),
                new CarRatingDto(
                        rating.getCar().getMarca(),
                        rating.getCar().getModelo(),
                        rating.getCar().getId().toString()
                ),
                rating.getRating(),
                rating.getComentario(),
                rating.getCreated_at(),
                rating.getLinks().toList()
        );
    }
}