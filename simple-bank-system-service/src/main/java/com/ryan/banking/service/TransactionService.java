package com.ryan.banking.service;

import com.ryan.banking.controller.dto.DepositDto;
import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.TransactionDepositRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionWithdrawRequestDto;
import com.ryan.banking.controller.dto.WithdrawDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Transaction;

public interface TransactionService {

    NewTransactionDto createTransaction(TransactionRequestDto txRequest)
            throws TransactionException, AccountNotFoundException;

    DepositDto deposit(TransactionDepositRequestDto txRequestDeposit)
            throws TransactionException, UserNotFoundException, AccountNotFoundException;

    WithdrawDto withdraw(TransactionWithdrawRequestDto txRequestWithdraw)
            throws TransactionException, UserNotFoundException, AccountNotFoundException;

    Transaction save(Transaction transaction);

}