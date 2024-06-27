package com.example.LibraryApplication.services;

import com.example.LibraryApplication.dto.BorrowRequest;
import com.example.LibraryApplication.dto.BorrowResponse;
import com.example.LibraryApplication.entities.Book;
import com.example.LibraryApplication.entities.Borrow;
import com.example.LibraryApplication.entities.Member;
import com.example.LibraryApplication.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    final BorrowRepository borrowRepository;
    BookService bookService;
    MemberService memberService;


    @Autowired
    public BorrowService(BorrowRepository borrowRepository){
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows(){
        return borrowRepository.findAll();
    }

    public List<Borrow> getBorrows(Book book, Member member, Date borrowDate, Date returnDate) {
        List<Borrow> borrows = borrowRepository.findAll();
        return borrows.stream()
                .filter(borrow -> (book == null || borrow.getBook().equals(book)) &&
                        (member == null || borrow.getMember().equals(member)) &&
                        (borrowDate == null || borrow.getBorrowDate().equals(borrowDate)) &&
                        (returnDate == null || borrow.getReturnDate().equals(returnDate)))
                .collect(Collectors.toList());
    }

    public Optional getBorrowByID(long id){
        return borrowRepository.findById(id);
    }

    public Borrow createBorrow(int bookid, int memberid, Date borrowDate, Date returnDate){
        Book borrowBook = bookService.getBookByID(bookid);
        Member borrowMember = memberService.getMemberByID(memberid);
        if(!borrowBook.getAvailable()){
            throw new RuntimeException("Book is not available!");
        }
        borrowBook.setAvailable(false);
        return borrowRepository.save(new Borrow(borrowBook,borrowMember,borrowDate,returnDate));
    }

    public Borrow updateBorrow(long id, Borrow newBorrow){
        Optional <Borrow> oldBorrowOptional = borrowRepository.findById(id);
        if(oldBorrowOptional.isPresent()){
            Borrow oldBorrow = oldBorrowOptional.get();
            if(!ObjectUtils.isEmpty(newBorrow.getBook())){
            oldBorrow.setBook(newBorrow.getBook());}
            if(!ObjectUtils.isEmpty(newBorrow.getMember())){
            oldBorrow.setMember(newBorrow.getMember());}
            if(!ObjectUtils.isEmpty(newBorrow.getBorrowDate())){
            oldBorrow.setBorrowDate(newBorrow.getBorrowDate());}
            if(!ObjectUtils.isEmpty(newBorrow.getReturnDate())){
            oldBorrow.setReturnDate(newBorrow.getReturnDate());}

            return borrowRepository.save(oldBorrow);
        }
        else{
            throw new RuntimeException("Borrow not found!");
        }
    }

    public void deleteBorrow(long id){
        if(borrowRepository.existsById(id)){
            borrowRepository.findById(id).get().getBook().setAvailable(true);
            borrowRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Borrow not found!");
        }
    }

    public BorrowResponse getBorrowsService(BorrowRequest borrowRequest){
        List<Borrow> resultBorrows = getBorrows(borrowRequest.getBook(),borrowRequest.getMember(),borrowRequest.getBorrowDate(),borrowRequest.getReturnDate());
        if(resultBorrows.isEmpty()){
            return new BorrowResponse("200", null, "Borrows cannot be found!");
        }
        return new BorrowResponse("200", resultBorrows, "List of the borrows");
    }


}
