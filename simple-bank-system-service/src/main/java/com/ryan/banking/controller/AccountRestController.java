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
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.service.AccountService;
import com.ryan.banking.util.AccountUtility;

@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {


}
