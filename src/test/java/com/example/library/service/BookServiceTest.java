package com.example.library.service;

import com.example.library.controller.BookController;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService mockBookService;
    private BookRepository mockBookRepository;

    public final Book input = new Book(null, "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book input2 = new Book(null, "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);
    public final Book recordWithId = new Book(UUID.randomUUID(), "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book recordWithId2 = new Book(recordWithId.getId(), "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);

    @BeforeEach
    public void setup() {
        mockBookRepository = Mockito.mock(BookRepository.class);
        mockBookService = new BookService(mockBookRepository);
    }

    @Test
    public void create_shouldReturnCreatedBook() {
        Mockito.when(mockBookRepository.save(Mockito.any())).thenReturn(recordWithId);
        Book response = mockBookService.create(input);
        assertEquals(recordWithId, response);
    }

    @Test
    public void getAllBooks_shouldReturnListOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(input);
        books.add(input2);
        Mockito.when(mockBookRepository.findAll()).thenReturn(books);
        List<Book> response = mockBookService.getAllBooks();
        assertEquals(books, response);
    }

    @Test
    public void findBookById_shouldReturnBook() {
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Book response = mockBookService.findBookById(recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void findBookByTitle_shouldReturnBook() {
        Mockito.when(mockBookRepository.findByTitle(recordWithId.getTitle())).thenReturn(Optional.of(recordWithId));
        Book response = mockBookService.findBookByTitle(recordWithId.getTitle());
        assertEquals(recordWithId, response);
    }

//    @Test
//    public void updateBook_shouldReturnUpdatedBook() {
//        Mockito.when(mockBookRepository.findById(Mockito.any())).thenReturn(Optional.of(recordWithId));
//        Book response = mockBookService.updateBook(input2, recordWithId.getId());
//        assertEquals(recordWithId2, response);
//    }

    @Test
    public void deleteBookById_shouldReturnDeletedBook() {
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Book response = mockBookService.deleteBookById(recordWithId.getId());
        assertEquals(recordWithId, response);
    }

}