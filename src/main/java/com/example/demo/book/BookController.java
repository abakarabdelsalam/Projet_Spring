package com.example.demo.book;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class BookController {
    @GetMapping(value="/books")
        public ResponseEntity listBooks(){
            Book book = new Book();
            book.setTitle("MyBook");
            book.setCategory(new Category("Db"));

            return new ResponseEntity(Arrays.asList(book), HttpStatus.OK);
        }

    @PostMapping("/books")
    public ResponseEntity addBook(Book book) {
        return new ResponseEntity(book, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId")  String bookId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping (value="/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId")  String bookId, Book book) {
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity listCategory(){
        Category category = new Category("Bd");
        Category categoryRoman = new Category("Roman");
        return new ResponseEntity(Arrays.asList(category,categoryRoman),HttpStatus.OK);

    }
}
