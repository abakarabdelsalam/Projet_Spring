package com.example.demo.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {

    //recherche par status et ne tiens pas le userId et ne veux pas le livre supprimé

    List<Book> findByStatusAndUserIdNotAndDeletedFalse(BookStatus status, Integer userId);


    //Les livre des utilisateurs connectées
    List<Book>findByUserIdAndDeletedFalse(Integer id);
}
