package com.movies.controllers;

import com.movies.dto.ReviewCreateDTO;
import com.movies.dto.ReviewResponseDTO;
import com.movies.dto.ReviewUpdateDTO;
import com.movies.entity.Movie;
import com.movies.entity.Review;
import com.movies.entity.User;
import com.movies.mapper.ReviewMapper;
import com.movies.security.services.UserDetailsImpl;
import com.movies.service.MoviesService;
import com.movies.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ReviewsController {

    private final ReviewService reviewService;
    private final MoviesService moviesService;

    @Autowired
    public ReviewsController(ReviewService reviewService, MoviesService moviesService) {
        this.reviewService = reviewService;
        this.moviesService = moviesService;
    }

    // GET /api/reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // GET /api/movies/{movieId}/reviews
    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByMovieId(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.findByMovieId(movieId);
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // POST /api/reviews
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@Valid @RequestBody ReviewCreateDTO reviewDTO,
                                                       @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Movie movie = moviesService.findById(reviewDTO.getMovieId());
        User createdBy = new User();
        createdBy.setId(currentUser.getId());
        createdBy.setUsername(currentUser.getUsername());
        createdBy.setEmail(currentUser.getEmail());

        Review review = ReviewMapper.toEntity(reviewDTO, movie, createdBy);
        review.setCreatedAt(LocalDateTime.now());
        Review saved = reviewService.save(review);
        return ResponseEntity.ok(ReviewMapper.toResponse(saved));
    }

    // PUT /api/reviews/{reviewId}
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewUpdateDTO reviewDTO,
                                                          @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Review existing = reviewService.findById(reviewId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        existing.setReview(reviewDTO.getReview());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setCreatedBy(existing.getCreatedBy());

        Review saved = reviewService.save(existing);
        return ResponseEntity.ok(ReviewMapper.toResponse(saved));
    }

    // DELETE /api/reviews/{reviewId}
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId,
                                               @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Review existing = reviewService.findById(reviewId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        reviewService.deleteById(reviewId);
        return ResponseEntity.ok("Deleted review id - " + reviewId);
    }
}
