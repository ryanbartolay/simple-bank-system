package com.ryan.banking.exception;

public class BalanceException extends TransactionException {

    private static final long serialVersionUID = 1L;

    public BalanceException(String debugMessage) {
        super(debugMessage);
    }
}
