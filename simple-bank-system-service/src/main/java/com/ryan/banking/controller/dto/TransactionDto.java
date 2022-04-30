package com.ryan.banking.controller.dto;

import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.model.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionDto {

    private String balanceRunning;
    private String completedDate;
    private String date;
    private String time;
    private String debitOrCredit;
    private String remarks;
    private String currency;
    private TransactionStatus status;
    private TransactionType type;

}
