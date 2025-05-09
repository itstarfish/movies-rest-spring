package com.movies.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ReviewCreateDTO {

    @NotBlank(message = "Review cannot be empty!")
    private String review;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must be at most 10")
    private Integer rating;

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    // Getters / Setters

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

}
