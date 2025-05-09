package com.movies.dao;

import com.movies.entity.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCategoryRepository extends JpaRepository<MovieCategory, Long> {
    MovieCategory findByName(String name);
}
