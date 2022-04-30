package com.ryan.banking.controller.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionWithdrawRequestDto {

    private UUID userId;
    private UUID accountId;
    private UUID transactionId;
    private Integer amount;
}
