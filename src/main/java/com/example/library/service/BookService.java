package com.example.library.service;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(Book book) {
        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getGenre(), book.getNumberOfPages(), book.getRating(), book.getHasRead());
        return bookRepository.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with id: " + id + " was not found.");
        }
        return optionalBook.get();
    }

    public Book findBookByTitle(String title) {
        Optional<Book> optionalBook = bookRepository.findByTitle(title);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with title: " + title + " was not found.");
        }
        return optionalBook.get();
    }

    public Book updateBook(Book book, UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with id: " + id + " was not found.");
        }
        Book updatedBook = new Book(id, book.getTitle(), book.getAuthor(), book.getGenre(), book.getNumberOfPages(), book.getRating(), book.getHasRead());
        return bookRepository.save(updatedBook);
    }

    public Book patchBookById(Book book, UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with id: " + id + " was not found.");
        }
        Book updatedBook = optionalBook.get();
        if (book.getTitle() != null) {
            updatedBook.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
            updatedBook.setAuthor(book.getAuthor());
        }
        if (book.getGenre() != null) {
            updatedBook.setGenre(book.getGenre());
        }
        if (book.getNumberOfPages() != null) {
            updatedBook.setNumberOfPages(book.getNumberOfPages());
        }
        if (book.getRating() != null) {
            updatedBook.setRating(book.getRating());
        }
        if (book.getHasRead() != null) {
            updatedBook.setHasRead(book.getHasRead());
        }
        return bookRepository.save(updatedBook);
    }

    public Book deleteBookById(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with id: " + id + " was not found.");
        }
        bookRepository.delete(optionalBook.get());
        return optionalBook.get();
    }
}
