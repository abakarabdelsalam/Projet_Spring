package com.example.demo.book;


import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.borrow.Borrow;
import com.example.demo.borrow.BorrowRepository;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRepository borrowRepository;


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
    public static Integer getUserConnectedId(){
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

        Optional<Book> bookToDelete = bookRepository.findById(Integer.valueOf(bookId));

            if (!bookToDelete.isPresent()){
                return new ResponseEntity("book not found", HttpStatus.BAD_REQUEST);
            }

            Book book = bookToDelete.get();
            List<Borrow> borrows = borrowRepository.findByBookId(book.getId());

                for (Borrow borrow:borrows){
                    if (borrow.getClosDate()==null){
                        User  borrower = borrow.getBorrower();
                        return new ResponseEntity(borrower,HttpStatus.CONFLICT);
                    }
                }
                book.setDeleted(true);
                bookRepository.save(book);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping (value="/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId")String bookId, @RequestParam @Valid Book book) {

        Optional<Book> bookToUpdate = bookRepository.findById(Integer.valueOf(bookId));
        if (!bookToUpdate.isPresent()){
            return new ResponseEntity("book not exist", HttpStatus.BAD_REQUEST);
        }

        Book bookToSave = bookToUpdate.get();
        Optional<Category> newCategory = categoryRepository.findById(book.getCategoryId());
        bookToSave.setCategory(newCategory.get());
        bookToSave.setTitle(book.getTitle());
        bookRepository.save(bookToSave);

        return new ResponseEntity(bookToSave, HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity listCategory(){
        Category category = new Category("Bd");
        Category categoryRoman = new Category("Roman");
        return new ResponseEntity(Arrays.asList(category,categoryRoman),HttpStatus.OK);
    }
    @GetMapping("/books/{bookId}")
        public ResponseEntity loadBook(@PathVariable("bookId") String bookId){
            Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

            if (!book.isPresent()){
                return new ResponseEntity("book not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(book.get(), HttpStatus.OK);
        }


    @GetMapping("/categories")
    public ResponseEntity listCategories(){
        return new ResponseEntity(categoryRepository.findAll(), HttpStatus.OK);

    }


}
