package com.example.library.service;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;
    private BookRepository mockBookRepository;

    public final Book input = new Book(null, "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book input2 = new Book(null, "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);
    public final Book recordWithId = new Book(UUID.randomUUID(), "Fairy Tale", "Stephen King", "Fantasy", 608, 3, true);
    public final Book recordWithId2 = new Book(recordWithId.getId(), "The Silent Patient", "Alex Michaelides", "Thriller", 336, 4, true);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockBookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookService(mockBookRepository);
    }

    @Test
    public void create_shouldReturnCreatedBook() {
        Mockito.when(mockBookRepository.save(Mockito.any())).thenReturn(recordWithId);
        Book response = bookService.create(input);
        assertEquals(recordWithId, response);
    }

    @Test
    public void getAllBooks_shouldReturnListOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(input);
        books.add(input2);
        Mockito.when(mockBookRepository.findAll()).thenReturn(books);
        List<Book> response = bookService.getAllBooks();
        assertEquals(books, response);
    }

    @Test
    public void findBookById_shouldReturnBook() {
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Book response = bookService.findBookById(recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void findBookById_throwsExceptionWhenBookWasNotFound() {
       Mockito.when(mockBookRepository.findById(id)).thenReturn(Optional.empty());
       BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.findBookById(id));
       assertEquals("A book with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void findBookByTitle_shouldReturnListOfBooks() {
        Mockito.when(mockBookRepository.findByTitle(recordWithId.getTitle())).thenReturn(List.of(recordWithId));
        List<Book> response = bookService.findBookByTitle(recordWithId.getTitle());
        assertEquals(List.of(recordWithId), response);
    }

    @Test
    public void updateBook_shouldReturnUpdatedBook() {
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenReturn(recordWithId);
        Book response = bookService.updateBook(input2, recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void updateBook_throwsExceptionWhenBookWasNotFound() {
        Mockito.when(mockBookRepository.findById(id)).thenReturn(Optional.empty());
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(input, id));
        assertEquals("A book with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedTitle() {
        Book input = new Book();
        input.setTitle("War and Peace");
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("War and Peace", response.getTitle());
        assertEquals("Stephen King", response.getAuthor());
        assertEquals("Fantasy", response.getGenre());
        assertEquals(608, response.getNumberOfPages());
        assertEquals(3, response.getRating());
        assertEquals(true, response.getHasRead());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedAuthor() {
        Book input = new Book();
        input.setAuthor("Michael Crichton");
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Fairy Tale", response.getTitle());
        assertEquals("Michael Crichton", response.getAuthor());
        assertEquals("Fantasy", response.getGenre());
        assertEquals(608, response.getNumberOfPages());
        assertEquals(3, response.getRating());
        assertEquals(true, response.getHasRead());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedGenre() {
        Book input = new Book();
        input.setGenre("Fiction");
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Fairy Tale", response.getTitle());
        assertEquals("Stephen King", response.getAuthor());
        assertEquals("Fiction", response.getGenre());
        assertEquals(608, response.getNumberOfPages());
        assertEquals(3, response.getRating());
        assertEquals(true, response.getHasRead());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedNumberOfPages() {
        Book input = new Book();
        input.setNumberOfPages(500);
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Fairy Tale", response.getTitle());
        assertEquals("Stephen King", response.getAuthor());
        assertEquals("Fantasy", response.getGenre());
        assertEquals(500, response.getNumberOfPages());
        assertEquals(3, response.getRating());
        assertEquals(true, response.getHasRead());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedRating() {
        Book input = new Book();
        input.setRating(4);
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Fairy Tale", response.getTitle());
        assertEquals("Stephen King", response.getAuthor());
        assertEquals("Fantasy", response.getGenre());
        assertEquals(608, response.getNumberOfPages());
        assertEquals(4, response.getRating());
        assertEquals(true, response.getHasRead());
    }

    @Test
    public void patchBookById_shouldReturnUpdatedHasRead() {
        Book input = new Book();
        input.setHasRead(false);
        Mockito.when(mockBookRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockBookRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Book response = bookService.patchBookById(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Fairy Tale", response.getTitle());
        assertEquals("Stephen King", response.getAuthor());
        assertEquals("Fantasy", response.getGenre());
        assertEquals(608, response.getNumberOfPages());
        assertEquals(3, response.getRating());
        assertEquals(false, response.getHasRead());
    }

    @Test
    public void patchBookById_throwsExceptionWhenBookWasNotFound() {
        Mockito.when(mockBookRepository.findById(id)).thenReturn(Optional.empty());
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.patchBookById(input, id));
        assertEquals("A book with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void deleteBookById_callsRepositoryDeleteMethod() {
        bookService.deleteBookById(id);
        Mockito.verify(mockBookRepository).deleteById(id);
    }

}