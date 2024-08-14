package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService mockBookService;

    private final Book book = new Book(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"), "firstName4", "lastName4", "email4@mail.com", 4, 3, false);

    @Test
    public void createBook() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/api/books")
                .content(asJsonString(new Book(null, "firstName4", "lastName4", "email4@mail.com", 4, 3, false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllBooks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/books").accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }

    @Test
    public void getBookById() throws Exception {
        Mockito.when(mockBookService.findBookById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(new Book());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/books/59c47568-fde0-4dd7-9aef-03db6a962810").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getBookByTitle() throws Exception {
        Mockito.when(mockBookService.findBookByTitle("Fairy Tale")).thenReturn(new Book());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/books/title/Fairy Tale").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateBook() throws Exception {
        Mockito.when(mockBookService.findBookById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(book);
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/books/59c47568-fde0-4dd7-9aef-03db6a962810")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void patchBook() throws Exception {
        Mockito.when(mockBookService.findBookById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(book);
        mvc.perform( MockMvcRequestBuilders
                        .patch("/api/books/59c47568-fde0-4dd7-9aef-03db6a962810")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookById() throws Exception {
        Mockito.when(mockBookService.findBookById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(book);
        mvc.perform( MockMvcRequestBuilders
                        .delete("/api/books/delete/59c47568-fde0-4dd7-9aef-03db6a962810")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
