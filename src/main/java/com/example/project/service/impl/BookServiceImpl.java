package com.example.project.service.impl;

import com.example.project.dto.BookDto;
import com.example.project.dto.CreateBookRequestDto;
import com.example.project.entity.Book;
import com.example.project.entity.Category;
import com.example.project.mapper.BookMapper;
import com.example.project.messaging.NotificationSender;
import com.example.project.repository.BookRepository;
import com.example.project.repository.CategoryRepository;
import com.example.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final NotificationSender notificationSender; // Впроваджуємо відправник повідомлень

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto requestDto) {
        if (bookRepository.existsByTitle(requestDto.getTitle())) {
            throw new RuntimeException("Книга з такою назвою вже існує!");
        }

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорію не знайдено з ID: " + requestDto.getCategoryId()));

        Book book = bookMapper.toEntity(requestDto, category);
        Book savedBook = bookRepository.save(book);

        notificationSender.sendNotification("Створено нову книгу: " + savedBook.getTitle() + " (Автор: " + savedBook.getAuthor() + ")");

        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорію не знайдено"));

        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setYear(requestDto.getYear());
        book.setCategory(category);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));
    }

    @Override
    public Page<BookDto> findByCategoryId(Long categoryId, Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId, pageable).map(bookMapper::toDto);
    }

    @Override
    public Page<BookDto> search(String query, Pageable pageable) {
        return bookRepository.searchBooks(query, pageable).map(bookMapper::toDto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Неможливо видалити: книгу не знайдено");
        }
        bookRepository.deleteById(id);
    }
}