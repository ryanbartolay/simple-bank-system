package com.ryan.banking.web.dto.utils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ryan.banking.controller.dto.TransactionDto;
import com.ryan.banking.model.Transaction;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.model.enums.TransactionType;

public class TransactionDtoUtility {

    public static final DateTimeFormatter DATEF = DateTimeFormat.forPattern("MMM dd");
    public static final DateTimeFormatter TIMEF = DateTimeFormat.forPattern("HH:mm");

    public static TransactionDto toTransactionDto(Transaction transaction) {
        String debitOrCredit = "";
        String currency = "";
        String balanceRunning = "";
        if (TransactionStatus.COMPLETED.equals(transaction.getStatus()) || 
                TransactionStatus.INVALID.equals(transaction.getStatus())) {
            if (TransactionType.WITHDRAW.equals(transaction.getType())) {
                debitOrCredit = "-" + transaction.getAmount().getNumber();
                balanceRunning = transaction.getBalanceRunning().getNumber().toString();
                currency = transaction.getBalanceRunning().getCurrency().getCurrencyCode();
            } else if (TransactionType.DEPOSIT.equals(transaction.getType())) {
                debitOrCredit = transaction.getAmount().getNumber().toString();
                balanceRunning = transaction.getBalanceRunning().getNumber().toString();
                currency = transaction.getBalanceRunning().getCurrency().getCurrencyCode();
            }
        }
        return TransactionDto.builder()
                .date(DATEF.print(transaction.getCompletedDate()))
                .time(TIMEF.print(transaction.getCompletedDate()))
                .type(transaction.getType())
                .status(transaction.getStatus())
                .debitOrCredit(debitOrCredit)
                .remarks(transaction.getRemarks())
                .currency(currency)
                .balanceRunning(balanceRunning)
                .build();
    }
}