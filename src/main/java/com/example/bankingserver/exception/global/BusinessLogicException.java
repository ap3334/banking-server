package com.example.bankingserver.exception.global;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final BasicExceptionType basicExceptionType;

    public BusinessLogicException(BasicExceptionType basicExceptionType) {

        super(basicExceptionType.getMessage());
        this.basicExceptionType = basicExceptionType;
    }
}
