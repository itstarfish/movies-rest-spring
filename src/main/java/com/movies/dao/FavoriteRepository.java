package com.movies.dao;

import com.movies.entity.Movie;
import com.movies.entity.User;
import com.movies.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUser(User user);
    List<Favorite> findAllByMovie(Movie movie);
    Optional<Favorite> findByUserAndMovie(User user, Movie movie);
}