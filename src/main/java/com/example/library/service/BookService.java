package com.example.library.service;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

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
            throw new BookNotFoundException("A book with that id was not found.");
        }
        return optionalBook.get();
    }

    public Book findBookByTitle(String title) {
        Optional<Book> optionalBook = bookRepository.findByTitle(title);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with that title was not found.");
        }
        return optionalBook.get();
    }

    public Book updateBook(Book book, UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with that id was not found.");
        }
        Book updatedBook = new Book(id, book.getTitle(), book.getAuthor(), book.getGenre(), book.getNumberOfPages(), book.getRating(), book.getHasRead());
        return bookRepository.save(updatedBook);
    }

    public Book patchBookById(Book book, UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with that id was not found.");
        }
        Book updatedBook = optionalBook.get();
        if (updatedBook.getTitle() != null) {
            updatedBook.setTitle(book.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            updatedBook.setAuthor(book.getAuthor());
        }
        if (updatedBook.getGenre() != null) {
            updatedBook.setGenre(book.getGenre());
        }
        if (updatedBook.getNumberOfPages() != null) {
            updatedBook.setNumberOfPages(book.getNumberOfPages());
        }
        if (updatedBook.getRating() != null) {
            updatedBook.setRating(book.getRating());
        }
        if (updatedBook.getHasRead() != null) {
            updatedBook.setHasRead(book.getHasRead());
        }
        return bookRepository.save(updatedBook);
    }

    public Book deleteBookById(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("A book with that id was not found.");
        }
        bookRepository.delete(optionalBook.get());
        return optionalBook.get();
    }
}
