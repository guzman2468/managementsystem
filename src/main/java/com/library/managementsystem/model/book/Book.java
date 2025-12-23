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

    @NotBlank()
    @Column(name = "title")
    private String title;

    @NotBlank()
    @Column(name = "author")
    private String author;

    @NotBlank()
    @Column(unique = true, name = "isbn")
    private String isbn;

    @NotBlank()
    @Column(name = "availableCopies")
    private String availableCopies;  //must be string for not blank to be used, use Integer.parseString(availableCopies) to make integer
}
