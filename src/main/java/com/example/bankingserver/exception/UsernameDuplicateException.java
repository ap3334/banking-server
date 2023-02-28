package com.example.bankingserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UsernameDuplicateException extends RuntimeException {

    public UsernameDuplicateException(String message) {
        super(message);
    }
}
