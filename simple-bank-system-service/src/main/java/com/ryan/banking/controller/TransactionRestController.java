package com.ryan.banking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryan.banking.controller.dto.DepositDto;
import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.TransactionDepositRequestDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionWithdrawRequestDto;
import com.ryan.banking.controller.dto.WithdrawDto;
import com.ryan.banking.service.TransactionService;

@RestController
@ControllerAdvice
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewTransactionDto> newTransaction(@Valid @RequestBody NewTransactionRequestDto txRequest)
            throws Exception {
        return ResponseEntity.ok(transactionService.createTransaction(txRequest));
    }

    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepositDto> deposit(@Valid @RequestBody TransactionDepositRequestDto txRequestDeposit)
            throws Exception {
        return ResponseEntity.ok(transactionService.deposit(txRequestDeposit));
    }

    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WithdrawDto> withdraw(@Valid @RequestBody TransactionWithdrawRequestDto txRequestWithdraw)
            throws Exception {
        return ResponseEntity.ok(transactionService.withdraw(txRequestWithdraw));
    }

}
