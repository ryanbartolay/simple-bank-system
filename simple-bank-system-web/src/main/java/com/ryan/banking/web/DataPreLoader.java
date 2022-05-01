package com.ryan.banking.web;

import javax.annotation.PostConstruct;
import javax.money.CurrencyUnit;

import org.javamoney.moneta.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.User;
import com.ryan.banking.model.converter.CurrencyUnitConverter;
import com.ryan.banking.model.enums.AccountType;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.model.enums.TransactionType;
import com.ryan.banking.repository.AccountRepository;
import com.ryan.banking.repository.TransactionRepository;
import com.ryan.banking.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataPreLoader {

    @Value("${bank.currency}")
    private String bankCurrency;

    DateTime now = DateTime.now();

    User user1 = User.builder()
            .email("ryank@peplink.com")
            .firstname("Ryan")
            .lastname("Kristoffer")
            .build();
    User user2 = User.builder()
            .email("bartolay.ryan@gmail.com")
            .firstname("Ryan")
            .lastname("Bartolay")
            .build();
    User user3 = User.builder()
            .email("bartolay13@gmail.com")
            .firstname("Kristoffer")
            .lastname("Bartolay")
            .build();
    User user4 = User.builder()
            .email("bartolay.joenna@gmail.com")
            .firstname("Joenna")
            .lastname("Bartolay")
            .build();
    User user5 = User.builder()
            .email("ericahmad@gmail.com")
            .firstname("Eric")
            .lastname("Ahmad")
            .build();
    User user6 = User.builder()
            .email("kenosp@gmail.com")
            .firstname("Keno")
            .lastname("San Pedro")
            .build();
    Account account10 = null;
    Account account20 = null;
    Account account21 = null;
    Account account22 = null;
    Account account23 = null;
    Account account24 = null;
    Account account25 = null;
    Account account26 = null;
    Account account27 = null;
    Account account28 = null;
    Account account29 = null;
    Account account30 = null;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyUnitConverter currencyUnitConverter;

    @PostConstruct
    public void initRecords() {
        initUsers();
        initAccounts();
        initTransactions();
    }

    private void initUsers() {
        user1 = userRepository.save(user1);
        log.info(user1.toString());
        user2 = userRepository.save(user2);
        log.info(user2.toString());
        user3 = userRepository.save(user3);
        log.info(user3.toString());
        user4 = userRepository.save(user4);
        log.info(user4.toString());
        user5 = userRepository.save(user5);
        log.info(user5.toString());
        user6 = userRepository.save(user6);
        log.info(user6.toString());
    }

    private void initAccounts() {
        CurrencyUnit currencyUnit = currencyUnitConverter.convertToEntityAttribute(bankCurrency);
        account10 = Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(1000, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user1)
                .build();
        account10 = accountRepository.save(account10);
        log.info(user1.toString());
        account20 = Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(0, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user2)
                .build();
        account21 = Account.builder()
                .type(AccountType.CHECKINGS)
                .balance(Money.of(1500, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user2)
                .build();
        account20 = accountRepository.save(account20);
        log.info(account20.toString());
        account21 = accountRepository.save(account21);
        log.info(account21.toString());
        // User 3
        account24 = accountRepository.save(Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(0, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user3)
                .build());
        account25 = accountRepository.save(Account.builder()
                .type(AccountType.CHECKINGS)
                .balance(Money.of(1500, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user3)
                .build());
        account26 = accountRepository.save(Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(5000, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user3)
                .build());
        // User 4
        account27 = accountRepository.save(Account.builder()
                .type(AccountType.CHECKINGS)
                .balance(Money.of(10500, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user4)
                .build());
        // User 5
        account28 = accountRepository.save(Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(5000, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user5)
                .build());
        // User 6
        account29 = accountRepository.save(Account.builder()
                .type(AccountType.CHECKINGS)
                .balance(Money.of(1500, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user6)
                .build());
        account30 = accountRepository.save(Account.builder()
                .type(AccountType.SAVINGS)
                .balance(Money.of(5000, bankCurrency))
                .currency(currencyUnit)
                .dateCreated(now)
                .user(user6)
                .build());
    }

    private void initTransactions() {
        Transaction tx1 = Transaction.builder()
                .account(account10)
                .amount(Money.of(1000, bankCurrency))
                .startDate(now.minusDays(1))
                .balanceStarting(Money.of(0, bankCurrency))
                .balanceRunning(Money.of(1000, bankCurrency))
                .expirationDate(now.minusDays(1).plusMinutes(3))
                .completedDate(now.minusDays(1))
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .build();
        transactionRepository.save(tx1);
    }
}
