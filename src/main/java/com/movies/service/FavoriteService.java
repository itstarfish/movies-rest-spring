package com.movies.service;

import com.movies.entity.Favorite;
import com.movies.entity.Movie;

import java.util.List;

/**
 * Service interface for managing favorites.
 */
public interface FavoriteService {

    /**
     * Retrieves all favorite items.
     *
     * @return a list of all favorite items
     */
    List<Favorite> findAll();

    /**
     * Retrieves favorite items associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of favorite items for the specified user
     */
    List<Favorite> findByUserId(Long userId);

    /**
     * Saves a new or updated favorite item.
     *
     * @param favorite the favorite item to save
     * @return the saved favorite entity
     */
    Favorite save(Favorite favorite);

    /**
     * Retrieves a favorite item by its unique ID.
     *
     * @param favoriteId the ID of the favorite item to retrieve
     * @return the favorite item with the specified ID
     */
    Favorite findById(Long favoriteId);

    /**
     * Deletes a favorite item by its unique ID.
     *
     * @param favoriteId the ID of the favorite item to delete
     */
    void deleteById(Long favoriteId);

    List<Movie> getFavoritesForUser(Long userId);

    void addFavorite(Long userId, Long movieId);

    void removeFavorite(Long userId, Long movieId);
}
