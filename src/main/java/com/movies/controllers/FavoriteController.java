package com.movies.controllers;

import com.movies.entity.Movie;
import com.movies.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFavorite(@RequestParam Long userId, @RequestParam Long movieId) {
        favoriteService.addFavorite(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam Long movieId) {
        favoriteService.removeFavorite(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Movie>> getFavoritesForUser(@PathVariable Long userId) {
        List<Movie> favorites = favoriteService.getFavoritesForUser(userId);
        return ResponseEntity.ok(favorites);
    }
}