package com.example.project.controller;

import com.example.project.AbstractBaseTest;
import com.example.project.entity.Category;
import com.example.project.messaging.NotificationSender;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // НОВИЙ ІМПОРТ
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookControllerTest extends AbstractBaseTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    // ЗАМІСТЬ @MockBean використовуємо @MockitoBean
    @MockitoBean
    private NotificationSender notificationSender;

    private Long catId;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        bookRepository.deleteAll();
        categoryRepository.deleteAll();

        Category cat = new Category();
        cat.setName("TestCategory");
        catId = categoryRepository.save(cat).getId();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateBook() throws Exception {
        String json = """
            {
                "title": "Docker Final Book",
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