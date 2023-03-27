package com.example.bankingserver.global.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final BasicExceptionType basicExceptionType;

    public BusinessLogicException(BasicExceptionType basicExceptionType) {

        super(basicExceptionType.getMessage());
        this.basicExceptionType = basicExceptionType;
    }
}
