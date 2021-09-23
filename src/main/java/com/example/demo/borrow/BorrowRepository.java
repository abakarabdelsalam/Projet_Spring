package com.example.demo.borrow;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BorrowRepository extends CrudRepository<Borrow,Integer> {

    //Les emprunt des utilisateurs connectées

    List<Borrow>findByBorrowerId(Integer borrowerId);

    //Suppression d'un livre qui n'est pas rattaché à un emprint
    List<Borrow>findByBookId(Integer bookId);
}
