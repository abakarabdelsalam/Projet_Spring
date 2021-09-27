package com.example.demo.book;


import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


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

        Integer userConnectedId = this.getUserConnectedId();
        Optional<User> user = userRepository.findById(userConnectedId);
        Optional<Category> category = categoryRepository.findById(book.getCategoryId());

        if (category.isPresent()){
            book.setCategory(category.get());
        }else {
            return new ResponseEntity("La categorie fournis n'est pas correct", HttpStatus.BAD_REQUEST);
        }
        if (user.isPresent()){
            book.setUser(user.get());
        }else{
            return new ResponseEntity("Le user fournis n'est pas correct", HttpStatus.BAD_REQUEST);
        }

        book.setDeleted(false);
        book.setBookStatus(BookStatus.FREE);
        bookRepository.save(book);
        return new ResponseEntity(book,HttpStatus.CREATED);

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
