package com.example.demo.borrow;

import com.example.demo.User.User;
import com.example.demo.book.Book;

import java.time.LocalDate;

public class Borrow {
    private User borrower;
    private User lender;
    private Book book;
    private LocalDate asDate;
    private LocalDate closDate;

    public LocalDate getAsDate() {
        return asDate;
    }

    public void setAsDate(LocalDate asDate) {
        this.asDate = asDate;
    }

    public LocalDate getClosDate() {
        return closDate;
    }

    public void setClosDate(LocalDate closDate) {
        this.closDate = closDate;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public User getLender() {
        return lender;
    }

    public void setLender(User lender) {
        this.lender = lender;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

