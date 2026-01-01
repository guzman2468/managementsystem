package com.library.managementsystem.model.book;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.managementsystem.model.MessageReponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse extends MessageReponse {

    private Integer id;
    private String author;
    private String title;
    private String isbn;
    private String availableCopies;

    public BookResponse(String message) {
        super(message);
    }

    public BookResponse(Integer id, String title, String author, String isbn, String availableCopies) {
        super(null);
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableCopies = availableCopies;

    }
}
