package com.ryan.banking.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ryan.banking.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

}
