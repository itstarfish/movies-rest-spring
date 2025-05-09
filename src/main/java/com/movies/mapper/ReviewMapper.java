package com.movies.mapper;

import com.movies.dto.ReviewResponseDTO;
import com.movies.dto.ReviewCreateDTO;
import com.movies.entity.Movie;
import com.movies.entity.Review;
import com.movies.entity.User;

import java.time.LocalDateTime;

public class ReviewMapper {

    public static Review toEntity(ReviewCreateDTO dto, Movie movie, User createdBy) {
        Review review = new Review();
        review.setReview(dto.getReview());
        review.setRating(dto.getRating());
        review.setMovie(movie);
        review.setCreatedBy(createdBy);
        return review;
    }

    public static ReviewResponseDTO toResponse(Review entity) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(entity.getId());
        dto.setReview(entity.getReview());
        dto.setRating(entity.getRating());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setMovieId(entity.getMovie().getId());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        return dto;
    }
}
