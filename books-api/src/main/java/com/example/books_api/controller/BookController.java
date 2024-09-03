package com.example.books_api.controller;

import com.example.books_api.BookRepository.BookRepository;
import com.example.books_api.exception.ResourceNotFoundException;

import com.example.books_api.model.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

//    @PostMapping
//    public Book createBook(@RequestBody Book book) {
//        return bookRepository.save(book);
//    }
    @PostMapping
    public ResponseEntity<Book> createdBook(@Valid @RequestBody Book book) {
        // save and return the book if validation passes
        Book createdBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    //Updating by ID
    @PutMapping("/{id}")
    public Book updateBook (@PathVariable Long id, @RequestBody Book bookdetails){
        //Find the book by ID, or throw an exception if not found
        Book book = bookRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("book not found with id "  + id));
      //  update the books fields with new data
        book.setTitle(bookdetails.getTitle());
        book.setAuthor(bookdetails.getAuthor());
        book.setIsbn(bookdetails.getIsbn());

        //save and return the updated book
        return bookRepository.save(book);
//
    }

    //Delete a book

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        //find the book by id or throw an exception if not found

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book not found with id "  + id));
        //delte the book from the repository
        bookRepository.delete(book);

        //return a 204 no content response to indicate successful deletion
        return ResponseEntity.noContent().build();
    }


    // pagination

    @GetMapping("/pagination")
    public Page<Book> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        //retrieve a page of books with specified page number and size
        return bookRepository.findAll(PageRequest.of(page, size));
    }


    //sorting
    @GetMapping("/pagination-sorting")
    public Page<Book> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        // retirieve a page of books with specified page number, size , and sorting

        return bookRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }


    // search method

    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn
    ){
        if(title != null ){
            return bookRepository.findByTitleContainingIgnoreCase(title);
        }else if (author != null ){
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (isbn !=null) {
            return bookRepository.findByIsbn(isbn);
        }
        return bookRepository.findAll();
    }


}
