package com.example.project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Author must not be empty")
    private String author;

    @NotNull(message = "Year must not be null")
    private Integer year;

    @NotNull(message = "Category ID must not be null")
    private Long categoryId;
}