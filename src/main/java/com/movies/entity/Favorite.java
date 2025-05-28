package com.movies.entity;

import jakarta.persistence.*;




@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // The user who added the movie to favorites.

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;  // The movie that was added to favorites.

    // Constructors, getters, setters...

    public Favorite() {}

    public Favorite(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}