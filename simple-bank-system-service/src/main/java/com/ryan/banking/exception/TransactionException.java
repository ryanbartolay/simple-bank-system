package com.ryan.banking.exception;

public class TransactionException extends Exception {

    private static final long serialVersionUID = 1L;

    public TransactionException(String debugMessage) {
        super(debugMessage);
    }
}
