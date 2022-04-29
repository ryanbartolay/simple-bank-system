package com.ryan.banking.service;

import java.util.List;
import java.util.UUID;

import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.User;

public interface UserService {

    List<User> findAll();

    User findUserById(UUID id) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

}
