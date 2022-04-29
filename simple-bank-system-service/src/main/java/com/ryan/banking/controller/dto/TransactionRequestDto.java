package com.ryan.banking.controller.dto;

import java.util.UUID;

import com.ryan.banking.controller.validator.AccountConstraint;
import com.ryan.banking.controller.validator.UserConstraint;
import com.ryan.banking.model.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionRequestDto {

    @UserConstraint
    private UUID userId;
    @AccountConstraint
    private UUID accountId;
    private TransactionType type;
}
