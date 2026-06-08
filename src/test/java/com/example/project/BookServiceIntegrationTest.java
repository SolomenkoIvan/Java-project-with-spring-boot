package com.example.project;

import com.example.project.AbstractBaseTest;
import com.example.project.dto.CreateBookRequestDto;
import com.example.project.entity.Category;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.BookRepository;
import com.example.project.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceIntegrationTest extends AbstractBaseTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

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
    void testSaveBookBusinessLogic() {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Service Layer Test");
        dto.setAuthor("Service Author");
        dto.setYear(2025);
        dto.setCategoryId(catId);

        bookService.save(dto);
        assertEquals(1, bookRepository.count());
    }
}