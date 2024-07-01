package com.example.LibraryApplication.dto;

import lombok.Data;
import java.util.Date;

@Data

public class BorrowRequest {
    private long id;
    private int bookId;
    private int memberId;
    private Date borrowDate;
    private Date returnDate;
}
