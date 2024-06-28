package com.example.LibraryApplication.controllers;

import com.example.LibraryApplication.dto.BookRequest;
import com.example.LibraryApplication.dto.BookResponse;
import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/books")

public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getall")
    public BookResponse getAllBooks(){
        try {
            return new BookResponse("200",bookService.getAllBooks(),"All books");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getbooks")
    public BookResponse getBooks(@RequestBody BookRequest bookRequest) {
        try {
            return bookService.getBooksService(bookRequest);
        } catch (Exception e) {
            return new BookResponse("500",null, "No book found!");
        }
    }

    @PostMapping("/create")
    public BookResponse createBook(@RequestBody BookRequest bookRequest) {
        try {
            Book book = new Book(bookRequest.getName(), bookRequest.getAuthor(), bookRequest.getPublisher(), true);
            Book created = bookService.createBook(book);
            List<Book> list = new ArrayList<>();
            list.add(created);
            return new BookResponse("200",list, "Creation successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public BookResponse deleteBook(@RequestBody BookRequest bookRequest) {
        try {
            List<Book> list = new ArrayList<>();
            list.add(bookService.getBookByID(bookRequest.getId()));
            bookService.deleteBook(bookRequest.getId());
            return new BookResponse("200",list, "Deletion successful");
        } catch (Exception e) {
            return new BookResponse("500",null, "No book found!");
        }
    }

    @PutMapping("/update")
    public BookResponse updateBook(@RequestBody BookRequest bookRequest) {
        try {
            Book newBook = new Book(bookRequest.getName(), bookRequest.getAuthor(), bookRequest.getPublisher(),bookRequest.getIsAvailable());
            Book updated = bookService.updateBook(bookRequest.getId(), newBook);
            List<Book> list = new ArrayList<>();
            list.add(updated);
            return new BookResponse("200",list, "Update successful");
        } catch (Exception e) {
            return new BookResponse("500",null, "No book found!");
        }
    }









}
