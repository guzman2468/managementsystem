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
}
