package com.example.books_api.BookRepository;



import com.example.books_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    //allow user to search for books based on attributes like title , author, or ISBN
        List<Book> findByTitleContainingIgnoreCase(String title);
        List<Book> findByAuthorContainingIgnoreCase(String author);
        List<Book> findByIsbn(String isbn);
    }

