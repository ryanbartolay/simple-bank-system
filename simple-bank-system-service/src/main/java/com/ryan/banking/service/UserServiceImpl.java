package com.ryan.banking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.User;
import com.ryan.banking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable("users")
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable("users")
    @Override
    public User findUserById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User ID not found"));
    }

    @Cacheable("users")
    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User email not found"));
    }

}
