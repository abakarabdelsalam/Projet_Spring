package com.example.demo.User;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @PostMapping(value = "/users")
    public ResponseEntity addUSer(@Valid @RequestParam User user){
      User userOld = new User("toto@gmail.com");
        return new ResponseEntity( userOld, HttpStatus.CREATED);

    }

}
