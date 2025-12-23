package com.library.managementsystem.controller;


import com.library.managementsystem.model.MessageReponse;
import com.library.managementsystem.model.book.Book;
import com.library.managementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //need a way to set the status code in postman to be an error not a 200
    }
}
