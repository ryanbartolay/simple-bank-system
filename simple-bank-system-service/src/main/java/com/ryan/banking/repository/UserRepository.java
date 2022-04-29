package com.ryan.banking.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ryan.banking.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Override
    List<User> findAll();

    Optional<User> findByEmail(String email);

}
