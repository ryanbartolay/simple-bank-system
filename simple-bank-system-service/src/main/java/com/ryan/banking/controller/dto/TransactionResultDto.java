package com.ryan.banking.controller.dto;

import org.javamoney.moneta.Money;
import org.joda.time.DateTime;

import com.ryan.banking.model.enums.TransactionStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionResultDto {

    private TransactionStatus status;
    private String remarks;
    private DateTime completedDate;
    private Money runningBalance;

}
