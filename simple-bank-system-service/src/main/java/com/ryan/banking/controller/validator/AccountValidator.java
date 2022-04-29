package com.ryan.banking.controller.validator;

import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.service.AccountService;

@Component
public class AccountValidator implements ConstraintValidator<AccountConstraint, UUID> {

    @Autowired
    private AccountService accountService;

    @Override
    public void initialize(AccountConstraint userConstraint) {
    }

    @Override
    public boolean isValid(UUID accountId, ConstraintValidatorContext cxt) {
        try {
            return !ObjectUtils.isEmpty(accountService.getAccountById(accountId));
        } catch (AccountNotFoundException e) {
            // We can suppress the thrown exception here, and use the constraints default
            // message.
            return false;
        }
    }

}
