package com.ryan.banking.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {

    Page<Transaction> findAllByAccount(Account account, Pageable pageable);

}
