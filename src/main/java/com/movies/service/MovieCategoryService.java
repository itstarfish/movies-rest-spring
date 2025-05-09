package com.movies.service;

import com.movies.entity.MovieCategory;
import com.movies.entity.User;

import java.util.List;

public interface MovieCategoryService {
    List<MovieCategory> findAll();

    MovieCategory findById(Long id);

    MovieCategory save(MovieCategory movieCategory);

    void deleteById(Long id);

    boolean existsByName(String name);
}
