package com.library.managementsystem.service;

import com.library.managementsystem.model.book.Book;
import com.library.managementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //create initial endpoints add more later
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addNewBook(Book book) {
        bookRepository.save(book);
    }

    public boolean checkDupeByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }


    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> findByTitleAndAuthorAndIsbn(String title, String author, String isbn) {
        return bookRepository.findByTitleAndAuthorAndIsbn(title, author, isbn);
    }

    public void deleteByTitleAndAuthorAndIsbn(String title, String author, String isbn) {
        bookRepository.deleteByTitleAndAuthorAndIsbn(title, author, isbn);
    }
}
