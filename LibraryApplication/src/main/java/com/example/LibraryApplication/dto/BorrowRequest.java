package com.example.LibraryApplication.dto;

import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.entities.Member;

import lombok.Data;
import java.util.Date;

@Data

public class BorrowRequest {
    private int id;
    private Book book;
    private Member member;
    private Date borrowDate;
    private Date returnDate;
}
