package com.movies.service;

import com.movies.dao.MovieCategoryRepository;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the MovieCategoryService interface for managing movie categories.
 */
@Service
public class MovieCategoryServiceImpl implements MovieCategoryService {

    // Repository for managing MovieCategory data
    private final MovieCategoryRepository movieCategoryRepository;

    /**
     * Constructor for injecting the MovieCategoryRepository dependency.
     *
     * @param movieCategoryRepository the repository for MovieCategory entities
     */
    @Autowired
    public MovieCategoryServiceImpl(MovieCategoryRepository movieCategoryRepository) {
        this.movieCategoryRepository = movieCategoryRepository;
    }

    /**
     * Retrieves a list of all available movie categories.
     *
     * @return a list of all movie categories
     */
    @Override
    public List<MovieCategory> findAll() {
        return movieCategoryRepository.findAll();
    }

    /**
     * Retrieves a movie category by its ID.
     *
     * @param id the unique ID of the movie category
     * @return the MovieCategory object with the specified ID
     * @throws RuntimeException if no movie category is found with the provided ID
     */
    @Override
    public MovieCategory findById(Long id) {
        return movieCategoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Did not find movie category id - " + id));
    }

    /**
     * Saves a new or updated movie category in the database.
     *
     * @param movieCategory the MovieCategory entity to save
     * @return the saved MovieCategory object
     */
    @Override
    public MovieCategory save(MovieCategory movieCategory) {
        return movieCategoryRepository.save(movieCategory);
    }

    /**
     * Deletes a movie category by its unique ID.
     *
     * @param id the ID of the movie category to delete
     */
    @Override
    public void deleteById(Long id) {
        movieCategoryRepository.deleteById(id);
    }

    /**
     * Checks if a movie category with the specified name exists.
     *
     * @param name the name of the movie category to check
     * @return true if a movie category with the given name exists, false otherwise
     */
    @Override
    public boolean existsByName(String name) {
        return movieCategoryRepository.findByName(name) != null;
    }

}
