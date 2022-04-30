package com.ryan.banking.controller.dto;

import java.util.UUID;

import com.ryan.banking.model.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionRequestDto {

    private UUID userId;
    private UUID accountId;
    private UUID transactionId;
    private Integer amount;
    private String transaction;
    private TransactionType type;

}
