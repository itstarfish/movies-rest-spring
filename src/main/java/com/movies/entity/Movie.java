package com.movies.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="movies")
public class Movie {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "IMDBrating")
    private Double IMDBrating;

    @Column(name = "movie_category_id")
    private Long movieCategory;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name="createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    // define constructors
    public Movie() {

    }

    public Movie(Long id, String name, String description, Double IMDBrating, Long movieCategory, User createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.IMDBrating = IMDBrating;
        this.movieCategory = movieCategory;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getIMDBrating() {
        return IMDBrating;
    }

    public void setIMDBrating(Double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(Long movieCategory) {
        this.movieCategory = movieCategory;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}