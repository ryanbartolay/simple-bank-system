package com.ryan.banking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.enums.TransactionStatus;

public interface TransactionService {

    Page<Transaction> findAllByAccount(Account account, Pageable pageable);

    NewTransactionDto createTransaction(NewTransactionRequestDto txRequest)
            throws TransactionException, AccountNotFoundException;

    TransactionResultDto processTransactionRequest(TransactionRequestDto transactionRequestDto)
            throws TransactionException, UserNotFoundException, AccountNotFoundException;

    Transaction save(Transaction transaction);

}
