package com.library.managementsystem.repository;

import com.library.managementsystem.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);
}
