package com.ryan.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.User;
import com.ryan.banking.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserInfo(@RequestParam(name = "email", required = true) String email)
            throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

}
