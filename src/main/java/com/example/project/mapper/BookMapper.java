package com.example.project.mapper;

import com.example.project.dto.BookDto;
import com.example.project.dto.CategoryDto;
import com.example.project.dto.CreateBookRequestDto;
import com.example.project.entity.Book;
import com.example.project.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        if (book == null) return null;
        CategoryDto categoryDto = null;
        if (book.getCategory() != null) {
            categoryDto = new CategoryDto(book.getCategory().getId(), book.getCategory().getName());
        }
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), categoryDto);
    }

    public Book toEntity(CreateBookRequestDto dto, Category category) {
        if (dto == null) return null;
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setYear(dto.getYear());
        book.setCategory(category);
        return book;
    }
}