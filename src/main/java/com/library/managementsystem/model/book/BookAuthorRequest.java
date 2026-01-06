package com.library.managementsystem.model.book;

import jakarta.validation.constraints.NotBlank;

public class BookAuthorRequest {

    @NotBlank
    private String author;

    public BookAuthorRequest(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
