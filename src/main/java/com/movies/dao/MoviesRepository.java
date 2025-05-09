package com.movies.dao;

import com.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movie,Integer> {
    Page<Movie> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
