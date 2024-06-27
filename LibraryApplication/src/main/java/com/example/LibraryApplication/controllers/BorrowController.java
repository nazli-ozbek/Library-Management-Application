package com.example.LibraryApplication.controllers;

import com.example.LibraryApplication.dto.BorrowRequest;
import com.example.LibraryApplication.dto.BorrowResponse;
import com.example.LibraryApplication.entities.Borrow;
import com.example.LibraryApplication.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/borrows")
public class BorrowController {
    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService){
        this.borrowService = borrowService;
    }

    @GetMapping("/getall")
    public BorrowResponse getAllBorrows(){
        try {
            return new BorrowResponse("200",borrowService.getAllBorrows(),"All borrows");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getborrows")
    public BorrowResponse getBorrows(@RequestBody BorrowRequest borrowRequest) {
        try {
            return borrowService.getBorrowsService(borrowRequest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/create")
    public BorrowResponse createBorrow(@RequestBody BorrowRequest borrowRequest) {
        try {
            Borrow borrow = new Borrow(borrowRequest.getBook(),borrowRequest.getMember(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
            Borrow created = borrowService.createBorrow(borrow);
            List<Borrow> list = new ArrayList<>();
            list.add(created);
            return new BorrowResponse("200",list, "Creation successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public BorrowResponse deleteBorrow(@RequestBody BorrowRequest borrowRequest) {
        try {
            Borrow borrow = borrowService.getBorrows(borrowRequest.getBook(),borrowRequest.getMember(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate()).get(0);
            List<Borrow> list = new ArrayList<>();
            list.add(borrow);
            borrowService.deleteBorrow(borrow.getId());
            return new BorrowResponse("200",list, "Deletion successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update")
    public BorrowResponse updateBorrow(@RequestBody BorrowRequest borrowRequest){
        try {
            Borrow newBorrow = new Borrow(borrowRequest.getBook(),borrowRequest.getMember(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
            Borrow updated = borrowService.updateBorrow(borrowRequest.getId(),newBorrow);
            List<Borrow> list = new ArrayList<>();
            list.add(updated);
            return new BorrowResponse("200",list, "Update successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
