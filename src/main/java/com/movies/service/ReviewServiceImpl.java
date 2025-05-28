package com.movies.service;

import com.movies.dao.ReviewRepository;
import com.movies.dao.UserRepository;
import com.movies.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing reviews.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for injecting the ReviewRepository dependency.
     *
     * @param reviewRepository the repository used for managing Review entities
     */
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;

    }

    /**
     * Retrieves all reviews from the database.
     *
     * @return a list of all reviews
     */
    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    /**
     * Retrieves reviews by a specific movie ID.
     *
     * @param movieId the ID of the movie whose reviews are to be retrieved
     * @return a list of reviews for the specified movie
     */
    @Override
    public List<Review> findByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    /**
     * Saves a new or updated review to the database.
     *
     * @param review the review entity to save
     * @return the saved review entity
     */
    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * Retrieves a review by its unique ID.
     *
     * @param reviewId the ID of the review to retrieve
     * @return the review with the specified ID
     * @throws RuntimeException if the review is not found
     */
    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id - " + reviewId));
    }

    /**
     * Deletes a review by its unique ID.
     *
     * @param reviewId the ID of the review to delete
     */
    @Override
    public void deleteById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
