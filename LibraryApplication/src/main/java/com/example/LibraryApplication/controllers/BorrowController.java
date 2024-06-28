package com.example.LibraryApplication.controllers;

import com.example.LibraryApplication.dto.BorrowRequest;
import com.example.LibraryApplication.dto.BorrowResponse;
import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.entities.Borrow;
import com.example.LibraryApplication.services.BookService;
import com.example.LibraryApplication.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @PostMapping("/create/{bookid}/{memberid}")
    public BorrowResponse createBorrow(@RequestBody BorrowRequest borrowRequest, @RequestParam long bookid, @RequestParam long memberid ) {
        try {
            Borrow created = borrowService.createBorrow(bookid,memberid,borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
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
            Borrow borrow = borrowService.getBorrows(borrowRequest.getBookId(),borrowRequest.getMemberId(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate()).get(0);
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
            Borrow updated = borrowService.updateBorrow(borrowRequest.getId(), borrowRequest.getBookId(), borrowRequest.getMemberId(), borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
            List<Borrow> list = new ArrayList<>();
            list.add(updated);
            return new BorrowResponse("200",list, "Update successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
