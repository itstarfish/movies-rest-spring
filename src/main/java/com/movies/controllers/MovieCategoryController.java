package com.movies.controllers;

import com.movies.dto.MovieCategoryCreateDTO;
import com.movies.dto.MovieCategoryResponseDTO;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;
import com.movies.security.services.UserDetailsImpl;
import com.movies.service.MovieCategoryService;
import com.movies.mapper.MovieCategoryMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category/")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class MovieCategoryController {

    private final MovieCategoryService movieCategoryService;

    @Autowired
    public MovieCategoryController(MovieCategoryService movieCategoryService) {
        this.movieCategoryService = movieCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<MovieCategoryResponseDTO>> findAll() {
        List<MovieCategory> categories = movieCategoryService.findAll();
        List<MovieCategoryResponseDTO> response = categories.stream()
                .map(MovieCategoryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<MovieCategoryResponseDTO> getCategory(@PathVariable Long categoryId) {
        MovieCategory category = movieCategoryService.findById(categoryId);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(category));
    }

    @PostMapping
    public ResponseEntity<MovieCategoryResponseDTO> addCategory(
            @Valid @RequestBody MovieCategoryCreateDTO categoryDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        if (movieCategoryService.existsByName(categoryDTO.getName())) {
            return ResponseEntity.badRequest().body(null);
        }

        User createdBy = new User();
        createdBy.setId(currentUser.getId());
        createdBy.setUsername(currentUser.getUsername());
        createdBy.setEmail(currentUser.getEmail());

        MovieCategory newCategory = MovieCategoryMapper.toEntity(categoryDTO, createdBy);
        newCategory.setCreatedAt(LocalDateTime.now());
        MovieCategory savedCategory = movieCategoryService.save(newCategory);
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(savedCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<MovieCategoryResponseDTO> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody MovieCategoryCreateDTO categoryDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        MovieCategory existing = movieCategoryService.findById(categoryId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        User createdBy = existing.getCreatedBy();
        MovieCategory updatedCategory = MovieCategoryMapper.toEntity(categoryDTO, createdBy);
        updatedCategory.setId(categoryId);
        updatedCategory.setCreatedAt(existing.getCreatedAt());
        updatedCategory.setUpdatedAt(LocalDateTime.now());

        MovieCategory savedCategory = movieCategoryService.save(updatedCategory);
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(savedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId,
                                                 @AuthenticationPrincipal UserDetailsImpl currentUser) {
        MovieCategory existing = movieCategoryService.findById(categoryId);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body("You are not authorized to delete this category");
        }

        movieCategoryService.deleteById(categoryId);
        return ResponseEntity.ok("Deleted movie category id - " + categoryId);
    }
}
