package com.example.library.controller;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private BookController bookController;
    private BookService mockBookService;

    public final Book input = new Book(null, "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book input2 = new Book(null, "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);
    public final Book recordWithId = new Book(UUID.randomUUID(), "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book recordWithId2 = new Book(recordWithId.getId(), "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockBookService = Mockito.mock(BookService.class);
        bookController = new BookController(mockBookService);
    }

    @Test
    public void createBook_shouldReturnBookAndCREATEDHttpStatus() {
        Mockito.when(mockBookService.create(Mockito.any())).thenReturn(recordWithId);
        ResponseEntity<Book> response = bookController.createBook(input);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getAllBooks_shouldReturnListOfBooksAndOKHttpStatus() {
        List<Book> books = new ArrayList<>();
        books.add(input);
        books.add(input2);
        Mockito.when(mockBookService.getAllBooks()).thenReturn(books);
        ResponseEntity<List<Book>> response = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    public void getBookById_shouldReturnBookAndOKHttpStatus() {
        Mockito.when(mockBookService.findBookById(recordWithId.getId())).thenReturn(recordWithId);
        ResponseEntity<Book> response = bookController.getBookById(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getBookById_shouldReturn404WhenBookNotFound() {
        Mockito.when(mockBookService.findBookById(id)).thenThrow(new BookNotFoundException("A book with that id was not found."));
        ResponseEntity<Book> response = bookController.getBookById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getBookByTitle_shouldReturnBookAndOKHttpStatus() {
        Mockito.when(mockBookService.findBookByTitle(recordWithId.getTitle())).thenReturn(recordWithId);
        ResponseEntity<Book> response = bookController.getBookByTitle(recordWithId.getTitle());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void updateBook_shouldReturnBookAndOKHttpStatus() {
        Mockito.when(mockBookService.updateBook(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Book> response = bookController.updateBook(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void patchBookById_shouldReturnBookAndOKHttpStatus() {
        Mockito.when(mockBookService.patchBookById(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Book> response = bookController.patchBookById(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void deleteBookById_shouldReturnBookAndOKHttpStatus() {
        Mockito.when(mockBookService.deleteBookById(recordWithId.getId())).thenReturn(recordWithId);
        ResponseEntity<Book> response = bookController.deleteBookById(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }
}