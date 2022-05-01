package com.ryan.banking.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryan.banking.controller.dto.AccountDto;
import com.ryan.banking.controller.dto.DatatableDto;
import com.ryan.banking.controller.dto.UserDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.RestUserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.User;
import com.ryan.banking.service.AccountService;
import com.ryan.banking.service.TransactionService;
import com.ryan.banking.service.UserService;
import com.ryan.banking.util.AccountUtility;
import com.ryan.banking.util.TransactionUtility;
import com.ryan.banking.util.UserUtility;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(required = true, value = "id") UUID id)
            throws RestUserNotFoundException {
        try {
            return ResponseEntity.ok(UserUtility.toUserDto(userService.findUserById(id)));
        } catch(Exception e) {
            throw new RestUserNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<DatatableDto<Object>> getUsers() {
        List<User> users = userService.findAll();
        if (!CollectionUtils.isEmpty(users)) {
            return ResponseEntity.ok(
                    DatatableDto.builder().data(
                    users.stream()
                        .filter(Objects::nonNull)
                        .map(UserUtility::toUserDto)
                        .collect(Collectors.toList()))
                    .build());
        }
        return ResponseEntity.ok(DatatableDto.builder().data(Collections.emptyList()).build());
    }

    @GetMapping(value = "/{userId}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatatableDto<Object>> getAccounts(
            @PathVariable(required = true, value = "userId") UUID userId) throws RestUserNotFoundException {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        if (!CollectionUtils.isEmpty(accounts)) {
            return ResponseEntity.ok(DatatableDto.builder().data(accounts.stream()
                    .filter(Objects::nonNull)
                    .map(AccountUtility::toAccountDto)
                    .collect(Collectors.toList()))
                    .build());
        }
        return ResponseEntity.ok(DatatableDto.builder().data(Collections.emptyList()).build());
    }

    @GetMapping(value = "/{userId}/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount(
            @PathVariable(required = true, value = "userId") UUID userId,
            @PathVariable(required = true, value = "accountId") UUID accountId)
            throws AccountNotFoundException {
        return ResponseEntity.ok(AccountUtility.toAccountDto(accountService.getAccountByIdAndUser(accountId, userId)));
    }

    @GetMapping(value = "/{userId}/transactions/{accountId}")
    public ResponseEntity<DatatableDto<Object>> transactions(Model model, @PathVariable(required = true, value = "userId") UUID userId,
            @PathVariable(required = true, value = "accountId") UUID accountId)
            throws RestUserNotFoundException {
        try {
            userService.findUserById(userId);
            Account account = accountService.getAccountById(accountId);
            List<Transaction> transactions = transactionService.findAllByAccount(account);
            if (!CollectionUtils.isEmpty(transactions)) {
                return ResponseEntity
                        .ok(DatatableDto.builder()
                                .data(transactions.stream().filter(Objects::nonNull)
                                        .map(TransactionUtility::toTransactionDto).collect(Collectors.toList()))
                                .build());
            }
            return ResponseEntity.ok(DatatableDto.builder().data(Collections.emptyList()).build());
        } catch (Exception e) {
            throw new RestUserNotFoundException(e.getMessage());
        }
    }
}
