package com.example.project.controller;

import com.example.project.dto.BookDto;
import com.example.project.dto.CreateBookRequestDto;
import com.example.project.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Наприклад, тільки адмін може додавати
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    public Page<BookDto> search(@RequestParam String query, Pageable pageable) {
        return bookService.search(query, pageable);
    }

    @GetMapping("/category/{id}")
    public Page<BookDto> getByCategory(@PathVariable Long id, Pageable pageable) {
        return bookService.findByCategoryId(id, pageable);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "Book deleted";
    }
}