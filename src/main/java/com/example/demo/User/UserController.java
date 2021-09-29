package com.example.demo.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/users")
    public ResponseEntity addUSer(@Valid @RequestParam User user){

        List<User> users = userRepository.findByEmailId(user.getEmail());

        if (!users.isEmpty()){
            return new ResponseEntity("User nexiste pas", HttpStatus.BAD_REQUEST);

        }

        userRepository.save(user);
        return new ResponseEntity( user, HttpStatus.CREATED);

    }

}
