package com.library.managementsystem.repository;

import com.library.managementsystem.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);

    // Query to search for values case INSENSITVE
    @Query(value = "SELECT * FROM book WHERE LOWER(title) = LOWER(:title)", nativeQuery = true)
    Optional<Book> findByTitle(@Param("title") String title);
}
