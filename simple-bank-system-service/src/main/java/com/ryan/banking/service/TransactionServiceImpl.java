package com.ryan.banking.service;

import java.util.UUID;
import java.util.function.Function;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ryan.banking.controller.dto.DepositDto;
import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.TransactionDepositRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.controller.dto.TransactionWithdrawRequestDto;
import com.ryan.banking.controller.dto.WithdrawDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.BalanceException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${bank.currency}")
    private String bankCurrency;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Creates and returns new Transaction
     *
     * @param user
     * @return
     * @throws AccountNotFoundException 
     * @throws TransactionException 
     */
    @Caching(put = @CachePut(cacheNames = "transaction", key ="#result.id"),
            evict = @CacheEvict(cacheNames = "transactions", allEntries = true))
    @Override
    public NewTransactionDto createTransaction(TransactionRequestDto txRequest) throws AccountNotFoundException, TransactionException {
        validateTransactionRequest(txRequest);
        Account account = accountService.getAccountByIdAndUser(txRequest.getAccountId(), txRequest.getUserId());
        Transaction tx = Transaction.builder()
                .id(UUID.randomUUID())
                .startDate(DateTime.now())
                .expirationDate(DateTime.now().plusMinutes(3))
                .status(TransactionStatus.NEW)
                .type(txRequest.getType())
                .account(account)
                .build();
        tx = transactionRepository.save(tx);
        return NewTransactionDto.builder()
                .id(tx.getId())
                .txDate(tx.getStartDate())
                .build();
    }

    // TODO : Refactor with withdraw
    @CacheEvict(cacheNames = "account", key = "#txRequestDeposit.accountId")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DepositDto deposit(TransactionDepositRequestDto txRequestDeposit) throws TransactionException, UserNotFoundException, AccountNotFoundException {
        Account account = accountService.getAccountByIdAndUser(txRequestDeposit.getAccountId(), txRequestDeposit.getUserId());
        Transaction tx = transactionRepository.findById(txRequestDeposit.getTransactionId())
                .orElseThrow(() -> new TransactionException("Transaction doesnt exists"));
        if (!tx.getAccount().equals(account)) {
            throw new TransactionException("Invalid Transaction");
        }
        tx.setAmount(Money.of(txRequestDeposit.getAmount(), Monetary.getCurrency(bankCurrency)));
        TransactionResultDto completedTx = transact(account, tx, account.getBalance()::add);
        return DepositDto.builder()
                .depositDate(completedTx.getCompletedDate())
                .status(completedTx.getStatus())
                .balance(completedTx.getRunningBalance())
                .amount(tx.getAmount())
                .remarks(completedTx.getRemarks())
                .build();
    }

    // TODO : Refactor with deposit
    @CacheEvict(cacheNames = "account", key = "#txRequestWithdraw.accountId")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public WithdrawDto withdraw(TransactionWithdrawRequestDto txRequestWithdraw) throws TransactionException, UserNotFoundException, AccountNotFoundException {
        Account account = accountService.getAccountByIdAndUser(txRequestWithdraw.getAccountId(), txRequestWithdraw.getUserId());
        Transaction tx = transactionRepository.findById(txRequestWithdraw.getTransactionId())
                .orElseThrow(() -> new TransactionException("Transaction doesnt exists"));
        if (!tx.getAccount().equals(account)) {
            throw new TransactionException("Invalid Transaction");
        }
        tx.setAmount(Money.of(txRequestWithdraw.getAmount(), Monetary.getCurrency(bankCurrency)));
        TransactionResultDto completedTx = transact(account, tx, account.getBalance()::subtract);
        return WithdrawDto.builder()
                .withdrawDate(completedTx.getCompletedDate())
                .status(completedTx.getStatus())
                .balance(completedTx.getRunningBalance())
                .amount(tx.getAmount())
                .remarks(completedTx.getRemarks())
                .build();
    }

    @CachePut(cacheNames = "transaction", key ="#result.id")
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public boolean isTransactionCompleted(TransactionStatus txStatus) {
        if (ObjectUtils.isEmpty(txStatus)) {
            return false;
        }
        return TransactionStatus.COMPLETED.equals(txStatus);
    }

    private void validateTransactionRequest(TransactionRequestDto txRequest) throws TransactionException {
        if (ObjectUtils.isEmpty(txRequest)) {
            throw new TransactionException("Invalid Transaction Request");
        }
        if (ObjectUtils.isEmpty(txRequest.getType())) {
            throw new TransactionException("Transaction Type is Required");
        }
    }

    private TransactionResultDto transact(Account account, Transaction tx, Function<Money, Money> func)
            throws TransactionException {
        try {
            DateTime now = DateTime.now();
            validateTransaction(tx);
            Money newBalance = func.apply(tx.getAmount());
            validateNewBalance(newBalance);
            account.setBalance(newBalance);
            account.setDateLastUpdate(now);
            tx.setBalanceRunning(newBalance);
            tx.setBalanceStarting(account.getBalance());
            tx.setCompletedDate(now);
            tx.setStatus(TransactionStatus.COMPLETED);
            accountService.save(account);
            tx = save(tx);
        } catch (Exception e) {
            tx.setBalanceRunning(account.getBalance());
            tx.setBalanceStarting(account.getBalance());
            tx.setCompletedDate(DateTime.now());
            tx.setStatus(TransactionStatus.INVALID);
            tx.setRemarks(e.getMessage());
            tx = save(tx);
        }
        return TransactionResultDto.builder()
                .status(tx.getStatus())
                .runningBalance(tx.getBalanceRunning())
                .completedDate(tx.getCompletedDate())
                .remarks(tx.getRemarks())
                .build();
    }

    private void validateTransaction(Transaction tx) throws TransactionException {
        if (ObjectUtils.isEmpty(tx)) {
            throw new TransactionException("Invalid Transaction");
        }
        if (!ObjectUtils.isEmpty(tx.getStatus())
                && !tx.getStatus().equals(TransactionStatus.NEW)) {
            throw new TransactionException("Requires NEW transaction");
        }
        CurrencyUnit currencyUnit = Monetary.getCurrency(bankCurrency);
        MonetaryAmount invalidAmount = Money.of(0, currencyUnit);
        if (tx.getAmount().isLessThan(invalidAmount)) {
            throw new TransactionException("Insufficient funds");
        }
    }

    private void validateNewBalance(Money money) throws BalanceException {
        MonetaryAmount dollars = Money.of(0, bankCurrency);
        if (money.isLessThan(dollars)) {
            throw new BalanceException("Insufficient funds");
        }
    }

}
