package com.ryan.banking.exception;

public class AccountNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String debugMessage) {
        super(debugMessage);
    }

}
