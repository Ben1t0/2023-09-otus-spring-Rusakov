package ru.otus.spring.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mappers.BookMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookPagesController.class)
@Import(BookMapper.class)
class BookPagesControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("Должен успешно возвращать страницу со списком всех книг")
    @Test
    void shouldReturnCorrectBookList() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

    @DisplayName("Должен успешно возвращать страницу добавления книги")
    @Test
    void shouldReturnCreatePage() throws Exception {
        mvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_book"));
    }

    @DisplayName("Должен успешно возвращать страницу редактирования")
    @Test
    void shouldReturnEditPage() throws Exception {
        mvc.perform(get("/books/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_book"))
                .andExpect(model().attributeExists("bookId"));
    }
}