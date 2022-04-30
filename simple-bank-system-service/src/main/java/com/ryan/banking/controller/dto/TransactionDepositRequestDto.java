package com.ryan.banking.controller.dto;

import java.util.UUID;

import com.ryan.banking.model.enums.TransactionType;

import lombok.Builder;

@Deprecated // TODO  Check if this class is still necessary
public class TransactionDepositRequestDto extends TransactionRequestDto {

    TransactionDepositRequestDto(UUID userId, UUID accountId, UUID transactionId, Integer amount, String transaction,
            TransactionType type) {
        super(userId, accountId, transactionId, amount, transaction, type);
        // TODO Auto-generated constructor stub
    }

}
