package com.movies.service;

import com.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoviesService {
    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByNameContaining(String name, Pageable pageable);

    Movie findById(long Id);

    Movie save(Movie movie);

    void deleteById(int theId);
}
