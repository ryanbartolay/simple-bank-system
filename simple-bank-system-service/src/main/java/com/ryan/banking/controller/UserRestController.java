package com.ryan.banking.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryan.banking.controller.dto.AccountDto;
import com.ryan.banking.controller.dto.UserDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.User;
import com.ryan.banking.service.AccountService;
import com.ryan.banking.service.UserService;
import com.ryan.banking.util.AccountUtility;
import com.ryan.banking.util.UserUtility;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(required = true, value = "id") UUID id)
            throws UserNotFoundException {
        return ResponseEntity.ok(UserUtility.toUserDto(userService.findUserById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findAll();
        if (!CollectionUtils.isEmpty(users)) {
            return ResponseEntity.ok(
                    users.stream()
                        .filter(Objects::nonNull)
                        .map(UserUtility::toUserDto)
                        .collect(Collectors.toList()));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{userId}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> getAccounts(
            @PathVariable(required = true, value = "userId") UUID userId) throws UserNotFoundException {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        if (!CollectionUtils.isEmpty(accounts)) {
            return ResponseEntity.ok(accounts.stream()
                    .filter(Objects::nonNull)
                    .map(AccountUtility::toAccountDto)
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(value = "/{userId}/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount(
            @PathVariable(required = true, value = "userId") UUID userId,
            @PathVariable(required = true, value = "accountId") UUID accountId)
            throws AccountNotFoundException {
        return ResponseEntity.ok(AccountUtility.toAccountDto(accountService.getAccountByIdAndUser(accountId, userId)));
    }

}
