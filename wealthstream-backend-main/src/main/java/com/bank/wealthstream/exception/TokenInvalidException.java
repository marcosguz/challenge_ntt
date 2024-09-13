package com.bank.wealthstream.exception;

public class TokenInvalidException extends Exception {
    private static final long serialVersionUID = 1L;

    public TokenInvalidException(String message) {
        super(message);
    }
}
