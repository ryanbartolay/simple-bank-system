package com.ryan.banking.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ryan.banking.model.Account;
import com.ryan.banking.model.User;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {

    List<Account> findByUser(User user);

    Optional<Account> findByIdAndUser(UUID id, User user);

}
