package com.example.LibraryApplication.repositories;

import com.example.LibraryApplication.entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Long> {
}
