package com.ryan.banking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.service.TransactionService;

@RestController
@RestControllerAdvice
@RequestMapping("/rest/transaction")
public class TransactionRestController implements DefaultTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewTransactionDto> newTransaction(@Valid @RequestBody NewTransactionRequestDto txRequest)
            throws Exception {
        return ResponseEntity.ok(transactionService.createTransaction(txRequest));
    }

    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResultDto> deposit(@Valid @RequestBody TransactionRequestDto txRequestDeposit)
            throws Exception {
        assignTransaction(txRequestDeposit);
        return ResponseEntity.ok(transactionService.processRESTTransactionRequest(txRequestDeposit));
    }

    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResultDto> withdraw(@Valid @RequestBody TransactionRequestDto txRequestWithdraw)
            throws Exception {
        assignTransaction(txRequestWithdraw);
        return ResponseEntity.ok(transactionService.processRESTTransactionRequest(txRequestWithdraw));
    }

}
