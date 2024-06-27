package com.example.LibraryApplication.dto;

import com.example.LibraryApplication.entities.Borrow;
import lombok.Data;
import java.util.List;

@Data
public class BorrowResponse {
    private String code;
    private List<Borrow> borrows;
    private String message;

    public BorrowResponse(String code, List<Borrow> borrows, String message){
        this.code = code;
        this.borrows = borrows;
        this.message = message;
    }
}
