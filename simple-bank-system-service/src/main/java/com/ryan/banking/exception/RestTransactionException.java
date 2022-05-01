package com.ryan.banking.exception;

public class RestTransactionException extends Exception {

    private static final long serialVersionUID = 1L;

    public RestTransactionException(String debugMessage) {
        super(debugMessage);
    }
}
