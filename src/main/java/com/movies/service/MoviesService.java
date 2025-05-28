package com.movies.service;

import com.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing movies.
 */
public interface MoviesService {

    /**
     * Retrieves all movies with pagination.
     *
     * @param pageable the pagination information
     * @return paginated list of movies
     */
    Page<Movie> findAll(Pageable pageable);

    /**
     * Retrieves movies by name containing the specified value, with pagination.
     *
     * @param name     the name to search for
     * @param pageable the pagination information
     * @return paginated list of movies that match the name criteria
     */
    Page<Movie> findByNameContaining(String name, Pageable pageable);

    /**
     * Retrieves a movie by its unique ID.
     *
     * @param Id the ID of the movie to retrieve
     * @return the movie with the specified ID
     */
    Movie findById(long Id);

    /**
     * Saves a new or updated movie entity.
     *
     * @param movie the movie to save
     * @return the saved movie entity
     */
    Movie save(Movie movie);

    /**
     * Deletes a movie by its unique ID.
     *
     * @param theId the ID of the movie to delete
     */
    void deleteById(int theId);
}
