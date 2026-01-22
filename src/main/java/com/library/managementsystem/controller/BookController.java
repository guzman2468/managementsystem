package com.library.managementsystem.controller;


import com.library.managementsystem.model.MessageResponse;
import com.library.managementsystem.model.book.*;
import com.library.managementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/getBooksByAuthor")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@Valid @RequestBody BookAuthorRequest request) {
        String author = request.getAuthor();
        List<Book> bookSearched = bookService.getBooksByAuthor(author.toLowerCase());

        if (!bookSearched.isEmpty()) {
            // convert List<Book> to List<BookResponse>
            List<BookResponse> responseList = bookSearched.stream()
                    .map(book -> {
                        BookResponse response = new BookResponse();
                        response.setTitle(book.getTitle());
                        response.setAuthor(book.getAuthor());
                        response.setIsbn(book.getIsbn());
                        response.setAvailableCopies(book.getAvailableCopies());
                        return response;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        } else {
            // return empty list with NOT_FOUND status for consistency
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping("/createBook")
    public ResponseEntity<MessageResponse> addNewBook(@RequestBody Book book) {

        MessageResponse response = new MessageResponse();

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

    @PutMapping("/checkoutBookByTitle")
    public ResponseEntity<MessageResponse> checkoutBookByTitle(@Valid @RequestBody BookTitleRequest request) {
        String title = request.getTitle().toLowerCase();
        Optional<Book> bookSearched = bookService.findByTitle(title);

        if (!bookSearched.isPresent()) {
            MessageResponse response = new MessageResponse();
            response.setMessage("No Book with that title was found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Book book = bookSearched.get();

        int availableCopies;
        try {
            availableCopies = Integer.parseInt(book.getAvailableCopies());
        } catch (NumberFormatException e) {
            // case where LIBRARIAN user may have inserted alphabetical data when the DB expects numerical values for availableCopies
            MessageResponse response = new MessageResponse();
            response.setMessage("Invalid available copies format");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (availableCopies <= 0) {
            MessageResponse response = new MessageResponse();
            response.setMessage("No copies available for checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        availableCopies--;
        book.setAvailableCopies(String.valueOf(availableCopies));

        bookService.updateBook(book);

        MessageResponse response = new MessageResponse();
        response.setMessage(book.getTitle() + " has been checked out successfully. Remaining copies: " + availableCopies);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/checkoutBookByIsbn")
    public ResponseEntity<MessageResponse> checkoutBookByIsbn(@Valid @RequestBody BookIsbnRequest request) {
        String isbn = request.getIsbn();
        Optional<Book> bookSearched = bookService.findByIsbn(isbn);

        if (!bookSearched.isPresent()) {
            MessageResponse response = new MessageResponse();
            response.setMessage("No Book with that ISBN was found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Book book = bookSearched.get();

        //
        int availableCopies;
        try {
            availableCopies = Integer.parseInt(book.getAvailableCopies());
        } catch (NumberFormatException e) {
            // case where LIBRARIAN user may have inserted alphabetical data when the DB expects numerical values for availableCopies
            MessageResponse response = new MessageResponse();
            response.setMessage("Invalid available copies format");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (availableCopies <= 0) {
            MessageResponse response = new MessageResponse();
            response.setMessage("No copies available for checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        availableCopies--;
        book.setAvailableCopies(String.valueOf(availableCopies));

        bookService.updateBook(book);

        MessageResponse response = new MessageResponse();
        response.setMessage(book.getTitle() + " has been checked out successfully. Remaining copies: " + availableCopies);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
