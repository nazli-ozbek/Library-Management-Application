package com.example.LibraryApplication.repositories;

import com.example.LibraryApplication.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

}
