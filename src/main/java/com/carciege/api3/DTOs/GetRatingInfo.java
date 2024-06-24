package com.carciege.api3.DTOs;

import org.springframework.hateoas.Link;
import java.time.LocalDateTime;
import com.carciege.api3.DTOs.rating.CarRatingDto;
import com.carciege.api3.DTOs.rating.UserRatingDto;

import java.util.List;
import java.util.UUID;

public record GetRatingInfo(
        UUID id,
        UserRatingDto user,
        CarRatingDto car,
        int rating,
        String comentario,
        String createdAt,
        List<Link> links
) {}
