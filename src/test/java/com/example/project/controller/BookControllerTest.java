package com.example.project.controller;

import com.example.project.AbstractBaseTest;
import com.example.project.entity.Category;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Вимикаємо фільтри, щоб не було конфлікту версій 4.0.5
public class BookControllerTest extends AbstractBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    private Long catId;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        categoryRepository.deleteAll();
        Category cat = new Category();
        cat.setName("TestCategory");
        catId = categoryRepository.save(cat).getId();
    }

    @Test
    void testCreateBook() throws Exception {
        String json = """
            {
                "title": "Test containers Book",
                "author": "Author",
                "year": 2024,
                "categoryId": %d
            }
            """.formatted(catId);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(1, bookRepository.count());
    }
}