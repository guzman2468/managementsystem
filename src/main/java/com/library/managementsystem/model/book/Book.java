package com.library.managementsystem.model.book;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title must not be empty or null.")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Author must not be empty or null.")
    @Column(name = "author")
    private String author;

    @NotBlank(message = "ISBN must not be empty or null.")
    @Column(unique = true, name = "isbn")
    private String isbn;

    //removal of NotBlank was needed, as input validation for a field to be filled out is handled by the front end, it was here originally when I was
    // testing out my code via postman requests
//    @NotBlank(message = "Available Copies must not be empty or null.")
    @Column(name = "availableCopies")
    private String availableCopies;  //must be string for not blank to be used, use Integer.parseString(availableCopies) to make integer
}
