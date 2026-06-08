package com.example.project.service;

import com.example.project.AbstractBaseTest;
import com.example.project.dto.CreateBookRequestDto;
import com.example.project.entity.Category;
import com.example.project.messaging.NotificationSender; // Імпорт
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Новий імпорт

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceIntegrationTest extends AbstractBaseTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @MockitoBean // Глушимо RabbitMQ для сервісного тесту
    private NotificationSender notificationSender;

    private Long catId;

    @BeforeEach
    void clean() {
        bookRepository.deleteAll();
        categoryRepository.deleteAll();
        Category cat = new Category();
        cat.setName("ServiceCategory");
        catId = categoryRepository.save(cat).getId();
    }

    @Test
    void shouldSaveBookSuccessfullyInDb() {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Service Test Book");
        dto.setAuthor("Service Author");
        dto.setYear(2025);
        dto.setCategoryId(catId);

        bookService.save(dto);

        assertEquals(1, bookRepository.count());
        assertTrue(bookRepository.existsByTitle("Service Test Book"));
    }
}