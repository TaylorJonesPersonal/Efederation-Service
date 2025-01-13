package com.efederation.Exception;

public class RefreshTokenExpiredException extends Exception {
    public RefreshTokenExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
