package com.ryan.banking.controller;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestType;

public interface DefaultTransactionController {

    default void assignTransaction(TransactionRequestDto txRequest) {
        if (ObjectUtils.isEmpty(txRequest) || !StringUtils.hasText(txRequest.getTransaction())) {
            return;
        }
        txRequest.setType(TransactionRequestType.valueOf(txRequest.getTransaction().toUpperCase()));
    }

}
