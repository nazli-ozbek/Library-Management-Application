package com.example.LibraryApplication.services;

import com.example.LibraryApplication.dto.BorrowRequest;
import com.example.LibraryApplication.dto.BorrowResponse;
import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.entities.Borrow;
import com.example.LibraryApplication.entities.Member;
import com.example.LibraryApplication.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final MemberService memberService;


    @Autowired
    public BorrowService(BorrowRepository borrowRepository, BookService bookService, MemberService memberService){
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public List<Borrow> getAllBorrows(){
        return borrowRepository.findAll();
    }

    public List<Borrow> getBorrows(long bookId, long memberId, Date borrowDate, Date returnDate) {
        List<Borrow> borrows = borrowRepository.findAll();
        return borrows.stream()
                .filter(borrow ->
                        (bookId == 0 || Objects.equals(borrow.getBook(), bookService.getBookByID(bookId))) &&
                                (memberId == 0 || Objects.equals(borrow.getMember(), memberService.getMemberByID(memberId))) &&
                                (borrowDate == null || Objects.equals(borrow.getBorrowDate(), borrowDate)) &&
                                (returnDate == null || Objects.equals(borrow.getReturnDate(), returnDate)))
                .collect(Collectors.toList());
    }

    public Optional getBorrowByID(long id){
        return borrowRepository.findById(id);
    }

    public Borrow createBorrow(long bookId, long memberId, Date borrowDate, Date returnDate){
        Book borrowBook = bookService.getBookByID(bookId);
        Member borrowMember = memberService.getMemberByID(memberId);
        if(!borrowBook.getAvailable()){
            throw new RuntimeException("Book is not available!");
        }
        borrowBook.setAvailable(false);
        return borrowRepository.save(new Borrow(borrowBook,borrowMember,borrowDate,returnDate));
    }

    public Borrow updateBorrow(long id, long bookId, long memberId, Date borrowDate, Date returnDate){
        Optional <Borrow> oldBorrowOptional = borrowRepository.findById(id);
        if(oldBorrowOptional.isPresent()){
            Borrow oldBorrow = oldBorrowOptional.get();
            if(bookId != 0){
            oldBorrow.setBook(bookService.getBookByID(bookId));}
            if(memberId != 0){
            oldBorrow.setMember(memberService.getMemberByID(memberId));}
            if(borrowDate != null){
            oldBorrow.setBorrowDate(borrowDate);}
            if(returnDate != null){
            oldBorrow.setReturnDate(returnDate);}

            return borrowRepository.save(oldBorrow);
        }
        else{
            throw new RuntimeException("Borrow not found!");
        }
    }

    public void deleteBorrow(long id){
        Optional <Borrow> borrowOptional = borrowRepository.findById(id);
        if(borrowOptional.isPresent()){
            Borrow borrow = borrowOptional.get();
            borrow.getBook().setAvailable(true);
            borrowRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Borrow not found!");
        }
    }

    public BorrowResponse getBorrowsService(BorrowRequest borrowRequest){
        List<Borrow> resultBorrows = getBorrows(borrowRequest.getBookId(),borrowRequest.getMemberId(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
        if(resultBorrows.isEmpty()){
            return new BorrowResponse("200", null, "Borrows cannot be found!");
        }
        return new BorrowResponse("200", resultBorrows, "List of the borrows");
    }


}
