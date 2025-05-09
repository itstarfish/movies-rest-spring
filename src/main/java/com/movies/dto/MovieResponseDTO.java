package com.movies.dto;

import java.time.LocalDateTime;

public class MovieResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double IMDBrating;
    private Long movieCategory;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Long getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(Long movieCategory) {
        this.movieCategory = movieCategory;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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


}

