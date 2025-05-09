package com.movies.dto;

import jakarta.validation.constraints.*;

public class MovieCategoryCreateDTO {

    @NotBlank(message = "A category must have a name")
    private String name;

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
