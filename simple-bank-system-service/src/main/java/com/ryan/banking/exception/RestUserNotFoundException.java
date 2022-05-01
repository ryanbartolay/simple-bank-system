package com.ryan.banking.exception;

public class RestUserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RestUserNotFoundException(String debugMessage) {
        super(debugMessage);
    }

}
