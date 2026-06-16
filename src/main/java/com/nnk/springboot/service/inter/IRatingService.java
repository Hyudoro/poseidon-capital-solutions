package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {

    List<Rating> findAll();

    Rating findById(Integer id);

    Rating save(Rating rating);

    Rating update(Integer id, Rating rating);

    void delete(Integer id);
}
