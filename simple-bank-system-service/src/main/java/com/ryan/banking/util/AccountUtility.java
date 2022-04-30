package com.ryan.banking.util;

import com.ryan.banking.controller.dto.AccountDto;
import com.ryan.banking.model.Account;

public class AccountUtility {

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .amount(account.getBalance().getNumber().intValue())
                .currency(account.getBalance().getCurrency().getCurrencyCode())
                .type(account.getType())
                .build();
    }

}
