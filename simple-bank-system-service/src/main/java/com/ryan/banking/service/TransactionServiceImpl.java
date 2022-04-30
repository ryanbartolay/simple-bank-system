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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ryan.banking.controller.dto.NewTransactionDto;
import com.ryan.banking.controller.dto.NewTransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestType;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.BalanceException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${bank.currency}")
    private String bankCurrency;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "transactions", key = "#account.id")
    @Override
    public Page<Transaction> findAllByAccount(Account account, Pageable pageable) {
        return transactionRepository.findAllByAccount(account, pageable);
    }

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
    public NewTransactionDto createTransaction(NewTransactionRequestDto txRequest) throws AccountNotFoundException, TransactionException {
        validateRequestNewTransaction(txRequest);
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

    @CacheEvict(cacheNames = "account", key = "#transactionRequestDto.accountId")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResultDto processTransactionRequest(TransactionRequestDto transactionRequestDto)
            throws TransactionException, UserNotFoundException, AccountNotFoundException {
        Account account = accountService.getAccountByIdAndUser(transactionRequestDto.getAccountId(), transactionRequestDto.getUserId());
        Transaction tx = transactionRepository.findById(transactionRequestDto.getTransactionId())
                .orElseThrow(() -> new TransactionException("Transaction doesnt exists"));
        try {
            if (TransactionRequestType.CANCEL.equals(transactionRequestDto.getType())) {
                return cancelTransaction(tx);
            }
            validateTransactionRequest(transactionRequestDto, tx);
            if (!tx.getAccount().equals(account)) {
                throw new TransactionException("Invalid Transaction");
            }
            Function<Money, Money> func = null;
            if (TransactionRequestType.DEPOSIT.equals(transactionRequestDto.getType())) {
                func = account.getBalance()::add;
            } else if(TransactionRequestType.WITHDRAW.equals(transactionRequestDto.getType())) {
                func = account.getBalance()::subtract;
            }        
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
                .amount(tx.getAmount())
                .runningBalance(tx.getBalanceRunning())
                .completedDate(tx.getCompletedDate())
                .type(tx.getType())
                .remarks(tx.getRemarks())
                .build();
    }

    @CachePut(cacheNames = "transaction", key ="#result.id")
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    private TransactionResultDto cancelTransaction(Transaction tx) {
        DateTime now = DateTime.now();
        tx.setCompletedDate(now);
        tx.setStatus(TransactionStatus.CANCELLED);
        tx.setRemarks(tx.getType() + " " + TransactionStatus.CANCELLED);
        tx = save(tx);
        return TransactionResultDto.builder()
                .status(tx.getStatus())
                .completedDate(tx.getCompletedDate())
                .build();
    }

    private void validateRequestNewTransaction(NewTransactionRequestDto txRequest) throws TransactionException {
        if (ObjectUtils.isEmpty(txRequest)) {
            throw new TransactionException("Invalid Transaction Request");
        }
        if (ObjectUtils.isEmpty(txRequest.getType())) {
            throw new TransactionException("Transaction Type is Required");
        }
    }

    private void validateTransactionRequest(TransactionRequestDto transactionRequestDto, Transaction transaction)
            throws TransactionException {
        if (ObjectUtils.isEmpty(transactionRequestDto) || ObjectUtils.isEmpty(transactionRequestDto.getType())) {
            throw new TransactionException("Invalid Transaction");
        }
        try {
            Integer amount = Integer.valueOf(transactionRequestDto.getAmount());
            if (TransactionRequestType.WITHDRAW.equals(transactionRequestDto.getType())) {
                if (amount < 100 || amount > 10000) {
                    throw new TransactionException("Withdraw amount should be between 100 up to 10000 only.");
                }
            } else if (TransactionRequestType.DEPOSIT.equals(transactionRequestDto.getType())) {
                if (amount < 1000 || amount > 1000000) {
                    throw new TransactionException("Deposit amount should be between 1000 up to 1000000 only.");
                }
            }
            transaction.setAmount(Money.of(amount, Monetary.getCurrency(bankCurrency)));
        } catch (TransactionException te) {
            throw te; // allow throwing of TransactionException
        } catch (Exception e) {
            log.error("Error getting amount", e);
            throw new TransactionException("Transaction amount is invalid");
        }
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
