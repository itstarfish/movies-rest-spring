package com.movies.mapper;

import com.movies.dto.MovieCategoryCreateDTO;
import com.movies.dto.MovieCategoryResponseDTO;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;

import java.time.LocalDateTime;

public class MovieCategoryMapper {

    public static MovieCategory toEntity(MovieCategoryCreateDTO dto, User createdBy) {
        MovieCategory category = new MovieCategory();
        category.setName(dto.getName());
        category.setCreatedBy(createdBy);
        return category;
    }

    public static MovieCategoryResponseDTO toResponse(MovieCategory entity) {
        MovieCategoryResponseDTO dto = new MovieCategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
