package com.example.LibraryApplication.services;

import com.example.LibraryApplication.dto.BookRequest;
import com.example.LibraryApplication.dto.BookResponse;
import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.repositories.BookRepository;
import com.example.LibraryApplication.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getBooks(String name, String author, String publisher, Boolean isAvailable) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(book -> (name == null || book.getName().equals(name)) &&
                        (author == null || book.getAuthor().equals(author)) &&
                        (publisher == null || book.getPublisher().equals(publisher)) &&
                        (isAvailable == null || book.getAvailable().equals(isAvailable)))
                .collect(Collectors.toList());
    }



    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBook(long id, Book newBook){
        Optional <Book> oldBookOptional = bookRepository.findById(id);
        if(oldBookOptional.isPresent()){
            Book oldBook = oldBookOptional.get();
            if(!ObjectUtils.isEmpty(newBook.getName())){
            oldBook.setName(newBook.getName());}
            if(!ObjectUtils.isEmpty(newBook.getAuthor())){
            oldBook.setAuthor(newBook.getAuthor());}
            if(!ObjectUtils.isEmpty(newBook.getPublisher())){
            oldBook.setPublisher(newBook.getPublisher());}
            if(!ObjectUtils.isEmpty(newBook.getAvailable())){
            oldBook.setAvailable(newBook.getAvailable());}

            return bookRepository.save(oldBook);
        }
        else{
            throw new RuntimeException("Book not found!");
        }
    }

    public void deleteBook(long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Book not found!");
        }
    }

    public BookResponse getBooksService(BookRequest bookRequest){
        List<Book> resultBooks = getBooks(bookRequest.getName(),bookRequest.getAuthor(), bookRequest.getPublisher(), bookRequest.getIsAvailable());
        if(resultBooks.isEmpty()){
            return new BookResponse("200", null, "Books cannot be found!");
        }
        return new BookResponse("200", resultBooks, "List of the books");
    }
}

