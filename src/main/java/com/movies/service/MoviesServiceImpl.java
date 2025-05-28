package com.movies.service;

import com.movies.dao.MoviesRepository;
import com.movies.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the MoviesService interface for managing movies.
 * Provides business logic and interacts with the MoviesRepository.
 */
@Service
public class MoviesServiceImpl implements MoviesService {

    // Repository for performing CRUD operations on Movie entities
    private MoviesRepository moviesRepository;

    /**
     * Constructor for injecting the MoviesRepository dependency.
     *
     * @param moviesRepository the MoviesRepository instance
     */
    @Autowired
    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    /**
     * Retrieves a paginated list of all movies.
     *
     * @param pageable the pagination information
     * @return a page of movies
     */
    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return moviesRepository.findAll(pageable);
    }

    /**
     * Retrieves a movie by its unique ID.
     *
     * @param Id the ID of the movie to retrieve
     * @return the movie with the specified ID
     * @throws RuntimeException if the movie with the provided ID is not found
     */
    @Override
    public Movie findById(long Id) {
        Optional<Movie> result = moviesRepository.findById((int) Id);

        Movie movie = null;

        if (result.isPresent()) {
            movie = result.get();
        } else {
            throw new RuntimeException("Did not find movie id - " + Id);
        }

        return movie;
    }

    /**
     * Saves a new or updated movie entity to the database.
     *
     * @param movie the movie to save
     * @return the saved movie entity
     */
    @Override
    public Movie save(Movie movie) {
        return moviesRepository.save(movie);
    }

    /**
     * Deletes a movie by its unique ID.
     *
     * @param id the ID of the movie to delete
     */
    @Override
    public void deleteById(int id) {
        moviesRepository.deleteById(id);
    }

    /**
     * Retrieves movies by name containing the specified text, with pagination.
     * The search is case-insensitive.
     *
     * @param name     the name to search for
     * @param pageable the pagination information
     * @return a page of movies that contain the specified name
     */
    public Page<Movie> findByNameContaining(String name, Pageable pageable) {
        return moviesRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}