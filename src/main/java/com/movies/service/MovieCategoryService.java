package com.movies.service;

import com.movies.entity.MovieCategory;
import com.movies.entity.User;

import java.util.List;

/**
 * Service interface for managing movie categories.
 */
public interface MovieCategoryService {

    /**
     * Retrieves all movie categories.
     *
     * @return a list of all movie categories
     */
    List<MovieCategory> findAll();

    /**
     * Retrieves a movie category by its unique ID.
     *
     * @param id the ID of the movie category to retrieve
     * @return the found MovieCategory object
     */
    MovieCategory findById(Long id);

    /**
     * Saves a new or updated movie category.
     *
     * @param movieCategory the movie category to save
     * @return the saved MovieCategory object
     */
    MovieCategory save(MovieCategory movieCategory);

    /**
     * Deletes a movie category by its unique ID.
     *
     * @param id the ID of the movie category to delete
     */
    void deleteById(Long id);

    /**
     * Checks if a movie category exists by its name.
     *
     * @param name the name of the movie category
     * @return true if a movie category with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}
