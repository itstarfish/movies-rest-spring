package com.movies.service;

import com.movies.entity.Review;

import java.util.List;

/**
 * Service interface for managing reviews.
 */
public interface ReviewService {

    /**
     * Retrieves all reviews.
     *
     * @return a list of all reviews
     */
    List<Review> findAll();

    /**
     * Retrieves reviews associated with a specific movie.
     *
     * @param movieId the ID of the movie
     * @return a list of reviews for the specified movie
     */
    List<Review> findByMovieId(Long movieId);

    /**
     * Saves a new or updated review.
     *
     * @param review the review to save
     * @return the saved review entity
     */
    Review save(Review review);

    /**
     * Retrieves a review by its unique ID.
     *
     * @param reviewId the ID of the review to retrieve
     * @return the review with the specified ID
     */
    Review findById(Long reviewId);

    /**
     * Deletes a review by its unique ID.
     *
     * @param reviewId the ID of the review to delete
     */
    void deleteById(Long reviewId);
}
