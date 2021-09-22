package com.example.demo.borrow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class BorrowController {

   @GetMapping(value = "/borrows")
    public ResponseEntity getMyBorrows(){
       Borrow borrow = new Borrow();
       borrow.setAsDate(LocalDate.now());
       return  new ResponseEntity(borrow, HttpStatus.OK);
   }
   @PostMapping("/borrows{bookId}")
     public ResponseEntity createBorrows(@PathVariable("bookId")String bookId){
       return new ResponseEntity(bookId, HttpStatus.CREATED);
   }
   @DeleteMapping("/borrows{borrowId}")
   public  ResponseEntity deletBorrow(@PathVariable("borrowId")String borrowId){
      return new ResponseEntity(borrowId,HttpStatus.NO_CONTENT);
   }
}
