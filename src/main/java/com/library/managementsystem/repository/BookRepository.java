package com.library.managementsystem.repository;

import com.library.managementsystem.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);

    // Query to search for values case INSENSITVE
    @Query(value = "SELECT * FROM book WHERE LOWER(title) = LOWER(:title)", nativeQuery = true)
    Optional<Book> findByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM book WHERE isbn = :isbn", nativeQuery = true)
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Query(value = "SELECT * FROM book WHERE LOWER(author) = LOWER(:author)", nativeQuery = true)
    List<Book> findBooksByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title) AND LOWER(b.author) = LOWER(:author) AND b.isbn = :isbn")
    Optional<Book> findByTitleAndAuthorAndIsbn(
            @Param("title") String title,
            @Param("author") String author,
            @Param("isbn") String isbn
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE LOWER(b.title) = LOWER(:title) AND LOWER(b.author) = LOWER(:author) AND b.isbn = :isbn")
    void deleteByTitleAndAuthorAndIsbn(
            @Param("title") String title,
            @Param("author") String author,
            @Param("isbn") String isbn
    );
}
