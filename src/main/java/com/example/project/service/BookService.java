package com.example.project.service;

import com.example.project.dto.BookDto;
import com.example.project.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);
    Page<BookDto> findAll(Pageable pageable);
    BookDto findById(Long id);
    BookDto update(Long id, CreateBookRequestDto requestDto);
    Page<BookDto> findByCategoryId(Long categoryId, Pageable pageable);
    Page<BookDto> search(String query, Pageable pageable);
    void deleteById(Long id);
}