package com.ryan.banking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.User;
import com.ryan.banking.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Cacheable(cacheNames = "accounts", key = "#user.id")
    @Override
    public List<Account> getAccountsByUser(User user) {
        return accountRepository.findByUser(user);
    }

    @Override
    public Account getAccountByIdAndUser(UUID id, UUID userId) throws AccountNotFoundException {
        return getAccountByIdAndUser(id, User.builder().id(userId).build());
    }

    @Override
    public Account getAccountByIdAndUser(UUID id, User user) throws AccountNotFoundException {
        if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(id)) {
            throw new AccountNotFoundException("Invalid account credentials");
        }
        return accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Cacheable(cacheNames = "account", key = "#id")
    @Override
    public Account getAccountById(UUID id) throws AccountNotFoundException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("ID : " + id));
    }

    @Caching(put = @CachePut(cacheNames = "account", key = "#result.id"),
            evict = @CacheEvict(cacheNames = "accounts", key = "#result.user.id"))
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
