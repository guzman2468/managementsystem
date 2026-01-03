package com.library.managementsystem.model.book;

import jakarta.validation.constraints.NotBlank;

public class BookIsbnRequest {

    @NotBlank
    private String isbn;

    public BookIsbnRequest(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
