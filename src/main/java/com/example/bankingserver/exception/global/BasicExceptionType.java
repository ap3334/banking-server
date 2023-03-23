package com.example.bankingserver.exception.global;

import org.springframework.http.HttpStatus;

public interface BasicExceptionType {

    String getErrorCode();

    String getMessage();

    HttpStatus getHttpStatus();

}
