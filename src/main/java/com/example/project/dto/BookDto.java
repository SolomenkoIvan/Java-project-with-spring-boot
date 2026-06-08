package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private CategoryDto category;
}