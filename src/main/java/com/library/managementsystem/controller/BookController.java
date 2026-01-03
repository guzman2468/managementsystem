package com.library.managementsystem.controller;


import com.library.managementsystem.model.MessageReponse;
import com.library.managementsystem.model.book.Book;
import com.library.managementsystem.model.book.BookIsbnRequest;
import com.library.managementsystem.model.book.BookResponse;
import com.library.managementsystem.model.book.BookTitleRequest;
import com.library.managementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getAllBooks() {
         return bookService.getAllBooks();
    }

    @GetMapping("/getBookByTitle")
    public ResponseEntity<BookResponse> getBookByTitle(@Valid @RequestBody BookTitleRequest request) {
        BookResponse response = new BookResponse();

        String title = request.getTitle().toLowerCase();
        Optional<Book> bookSearched = bookService.findByTitle(title.toLowerCase());

        if (bookSearched.isPresent()) {
            Book bookToBeSentAsResponse = bookSearched.get();
            response.setTitle(bookToBeSentAsResponse.getTitle());
            response.setAuthor(bookToBeSentAsResponse.getAuthor());
            response.setIsbn(bookToBeSentAsResponse.getIsbn());
            response.setAvailableCopies(bookToBeSentAsResponse.getAvailableCopies());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setMessage("No Book with that title was found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getBookByIsbn")
    public ResponseEntity<BookResponse> getBookByIsbn(@Valid @RequestBody BookIsbnRequest request) {
        BookResponse response = new BookResponse();

        String isbn = request.getIsbn();
        Optional<Book> bookSearched = bookService.findByIsbn(isbn);

        if (bookSearched.isPresent()) {
            Book bookToBeSentAsResponse = bookSearched.get();
            response.setTitle(bookToBeSentAsResponse.getTitle());
            response.setAuthor(bookToBeSentAsResponse.getAuthor());
            response.setIsbn(bookToBeSentAsResponse.getIsbn());
            response.setAvailableCopies(bookToBeSentAsResponse.getAvailableCopies());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setMessage("No Book with that ISBN was found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/createBook")
    public ResponseEntity<MessageReponse> addNewBook(@RequestBody Book book) {

        MessageReponse response = new MessageReponse();

        //implement error handling later on

        boolean isPresent = bookService.checkDupeByIsbn(book.getIsbn());

        if (isPresent) {
            response.setMessage("Book with that isbn already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            bookService.addNewBook(book);
            response.setMessage("Book added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
