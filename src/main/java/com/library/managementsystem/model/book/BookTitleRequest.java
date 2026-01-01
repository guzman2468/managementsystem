package com.library.managementsystem.model.book;

import jakarta.validation.constraints.NotBlank;

public class BookTitleRequest {

    @NotBlank
    private String title;

    public BookTitleRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
