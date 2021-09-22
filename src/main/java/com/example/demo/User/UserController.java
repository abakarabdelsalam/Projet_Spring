package com.example.demo.User;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping(value = "/users")
    public ResponseEntity addUSer(User user){
      User userOld = new User("toto@gmail.com");
        return new ResponseEntity( userOld, HttpStatus.CREATED);

    }

}
