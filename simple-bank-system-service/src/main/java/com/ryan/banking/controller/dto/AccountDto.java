package com.ryan.banking.controller.dto;

import java.util.UUID;

import com.ryan.banking.model.enums.AccountType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {

    private UUID id;
    private AccountType type;
    private String currency;
    private Integer amount;

}
