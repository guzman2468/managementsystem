package com.library.managementsystem.service;

import com.library.managementsystem.model.book.Book;
import com.library.managementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
