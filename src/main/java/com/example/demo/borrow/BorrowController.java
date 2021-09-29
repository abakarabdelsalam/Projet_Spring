package com.example.demo.borrow;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.book.Book;
import com.example.demo.book.BookController;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
public class BorrowController {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

   @GetMapping(value = "/borrows")
    public ResponseEntity getMyBorrows(){

       Integer userConnectedId =   BookController.getUserConnectedId();
       List<Borrow> borrows = borrowRepository.findByBorrowerId(userConnectedId);

       return  new ResponseEntity(borrows, HttpStatus.OK);
   }
   @PostMapping("/borrows{bookId}")
     public ResponseEntity createBorrows(@PathVariable("bookId")String bookId){

       Integer userConnectedId = BookController.getUserConnectedId();
       Optional<User> borrower = userRepository.findById(Integer.valueOf(userConnectedId));
       Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

       if (borrower.isPresent() && book.isPresent()){
           Borrow borrow = new Borrow();
           borrow.setBook(book.get());
           borrow.setBorrower(borrower.get());
           borrow.setLender(book.get().getUser());
           borrow.setAsDate(LocalDate.now());
           borrowRepository.save(borrow);

           book.get().setStatus(BookStatus.BORROWED);
           bookRepository.save(book.get());

           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity(bookId, HttpStatus.CREATED);
   }
   @DeleteMapping("/borrows{borrowId}")
   public  ResponseEntity deletBorrow(@PathVariable("borrowId")String borrowId){

       Optional<Borrow> borrow = borrowRepository.findById(Integer.valueOf(borrowId));
       if (!borrow.isPresent()){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
       Borrow borrow1 = borrow.get();
       borrow1.setClosDate(LocalDate.now());
       borrowRepository.save(borrow1);


       Book book = borrow1.getBook();
       book.setStatus(BookStatus.FREE);
       bookRepository.save(book);


      return new ResponseEntity(borrowId,HttpStatus.NO_CONTENT);
   }
}
