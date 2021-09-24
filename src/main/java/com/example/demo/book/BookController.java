package com.example.demo.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value="/books")
        public ResponseEntity listBooks(@RequestParam(required = false)BookStatus status){

        Integer userConnectedId = this.getUserConnectedId();

            List<Book>books;
        //free books
            if (status != null && status == BookStatus.FREE){

                books = bookRepository.findByStatusAndUserIdNotAndDeletedFalse(status,userConnectedId);
            //My books
            } else{
                books = bookRepository.findByUserIdAndDeletedFalse(userConnectedId);
            }

            return new ResponseEntity(books, HttpStatus.OK);
        }

        private Integer getUserConnectedId(){
        return 1;
        }

    @PostMapping("/books")
    public ResponseEntity addBook(@RequestParam Book book) {
        return new ResponseEntity(book, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId")  String bookId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping (value="/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId")String bookId, @RequestParam @Valid Book book) {
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity listCategory(){
        Category category = new Category("Bd");
        Category categoryRoman = new Category("Roman");
        return new ResponseEntity(Arrays.asList(category,categoryRoman),HttpStatus.OK);

    }
}
