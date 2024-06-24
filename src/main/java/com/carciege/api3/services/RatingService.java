package com.carciege.api3.services;

import com.carciege.api3.models.Rating;
import com.carciege.api3.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(String id) {
        return ratingRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void deleteById(String id) {
        ratingRepository.deleteById(UUID.fromString(id));
    }
}

