package com.ryan.banking.web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.User;
import com.ryan.banking.model.enums.TransactionType;
import com.ryan.banking.service.AccountService;
import com.ryan.banking.service.TransactionService;
import com.ryan.banking.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping(value = "/{id}")
    public String viewUser(Model model, @PathVariable(required = true, value = "id") UUID id)
            throws UserNotFoundException {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("accounts", accountService.getAccountsByUser(user));
        return "user/user";
    }

    @GetMapping(value = "/{userId}/deposit/{accountId}")
    public String deposit(Model model, @PathVariable(required = true, value = "userId") UUID userId,
            @PathVariable(required = true, value = "accountId") UUID accountId)
            throws UserNotFoundException, TransactionException, AccountNotFoundException {
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("account", accountService.getAccountById(accountId));
        NewTransactionDto newTransactionDto = transactionService
        .createTransaction(NewTransactionRequestDto.builder()
                .accountId(accountId)
                .userId(userId)
                .type(TransactionType.DEPOSIT)
                .build());
        model.addAttribute("deposit", TransactionRequestDto.builder()
                .userId(userId)
                .accountId(accountId)
                .transactionId(newTransactionDto.getId())
                .build());
        return "user/deposit";
    }

    @GetMapping(value = "/{userId}/withdraw/{accountId}")
    public String withdraw(Model model, @PathVariable(required = true, value = "userId") UUID userId,
            @PathVariable(required = true, value = "accountId") UUID accountId)
            throws UserNotFoundException, TransactionException, AccountNotFoundException {
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("account", accountService.getAccountById(accountId));
        NewTransactionDto newTransactionDto = transactionService
                .createTransaction(NewTransactionRequestDto.builder()
                .accountId(accountId)
                .userId(userId)
                .type(TransactionType.WITHDRAW)
                .build());
        model.addAttribute("withdraw", TransactionRequestDto.builder()
                .userId(userId)
                .accountId(accountId)
                .transactionId(newTransactionDto.getId())
                .build());
        return "user/withdraw";
    }
}