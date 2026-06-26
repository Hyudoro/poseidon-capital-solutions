package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.inter.IRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements IRatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> findAll() {
        log.debug("Fetching all ratings");
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        log.debug("Fetching rating by id={}", id);
        return ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found for id={}", id);
                    return new IllegalArgumentException("Invalid Rating Id: " + id);
                });
    }

    @Override
    public Rating save(Rating rating) {
        log.info("Saving new rating");
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Integer id, Rating rating) {
        ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid Rating Id: " + id);
                });
        rating.setId(id);
        log.info("Updating rating id={}", id);
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(Integer id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid Rating Id: " + id);
                });
        log.info("Deleting rating id={}", id);
        ratingRepository.delete(rating);
    }
}
