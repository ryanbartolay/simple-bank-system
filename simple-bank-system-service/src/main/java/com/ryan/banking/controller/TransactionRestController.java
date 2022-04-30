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

import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestType;
import com.ryan.banking.controller.dto.TransactionResultDto;
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
    public ResponseEntity<TransactionResultDto> deposit(@Valid @RequestBody TransactionRequestDto txRequestDeposit)
            throws Exception {
        txRequestDeposit.setType(TransactionRequestType.DEPOSIT);
        return ResponseEntity.ok(transactionService.processTransactionRequest(txRequestDeposit));
    }

    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResultDto> withdraw(@Valid @RequestBody TransactionRequestDto txRequestWithdraw)
            throws Exception {
        txRequestWithdraw.setType(TransactionRequestType.WITHDRAW);
        return ResponseEntity.ok(transactionService.processTransactionRequest(txRequestWithdraw));
    }

}
