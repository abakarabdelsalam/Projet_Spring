package com.example.demo.borrow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow,Integer> {

    //Les emprunt des utilisateurs connectées

    List<Borrow>findByBorrowerId(Integer borrowerId);

    //Suppression d'un livre qui n'est pas rattaché à un emprint
    List<Borrow>findByBookId(Integer bookId);
}
