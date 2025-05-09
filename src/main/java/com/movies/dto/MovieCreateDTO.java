package com.movies.dto;

import jakarta.validation.constraints.*;

public class MovieCreateDTO {

    @NotBlank(message = "A movie must have a name")
    private String name;

    @Size(max = 255, message = "Description must be 255 characters or less")
    private String description;

    @DecimalMin(value = "0", message = "Ranking must be min 0")
    @DecimalMax(value = "10", message = "Ranking must be max 10")
    private Double IMDBrating = 4.5; // default

    @NotNull(message = "A movie must belong to a category")
    private Long movieCategory;


    // Getters & Setters

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

}
