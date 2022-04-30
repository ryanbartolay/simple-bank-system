package com.ryan.banking.service;

import java.util.List;
import java.util.UUID;

import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.User;

public interface AccountService {

    public List<Account> getAccountsByUser(User user);

    public List<Account> getAccountsByUserId(UUID userId);

    public Account getAccountByIdAndUser(UUID id, User user) throws AccountNotFoundException;

    public Account getAccountByIdAndUser(UUID id, UUID userId) throws AccountNotFoundException;

    public Account getAccountById(UUID accountId) throws AccountNotFoundException;

    public Account save(Account account);

}
