package com.movies.service;

import com.movies.dao.FavoriteRepository;
import com.movies.dao.MovieCategoryRepository;
import com.movies.dao.MoviesRepository;
import com.movies.dao.UserRepository;
import com.movies.entity.Favorite;
import com.movies.entity.Movie;
import com.movies.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MoviesService moviesService;
    private final MoviesRepository moviesRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, MoviesService moviesService, MoviesRepository moviesRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.moviesService = moviesService;
        this.moviesRepository = moviesRepository;
    }

    public void addFavorite(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Movie movie = moviesService.findById(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found");
        }

        if (favoriteRepository.findByUserAndMovie(user, movie).isPresent()) {
            throw new IllegalArgumentException("Movie is already in favorites");
        }

        Favorite favorite = new Favorite(user, movie);
        favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Movie movie = moviesRepository.findById(movieId.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Favorite favorite = favoriteRepository.findByUserAndMovie(user, movie)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

    public List<Movie> getFavoritesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Favorite> favorites = favoriteRepository.findAllByUser(user);
        return favorites.stream()
                .map(Favorite::getMovie)
                .collect(Collectors.toList());
    }

    @Override
    public List<Favorite> findAll() {
        return List.of();
    }

    @Override
    public List<Favorite> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Favorite save(Favorite favorite) {
        return null;
    }

    @Override
    public Favorite findById(Long favoriteId) {
        return null;
    }

    @Override
    public void deleteById(Long favoriteId) {

    }
}