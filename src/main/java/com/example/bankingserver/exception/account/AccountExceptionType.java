package com.example.bankingserver.exception.account;

import com.example.bankingserver.exception.global.BasicExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountExceptionType implements BasicExceptionType {

    NOT_FOUND_ACCOUNT("NOT_FOUND_ACCOUNT", "해당 계좌는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_ENOUGH_AMOUNT("NOT_ENOUGH_AMOUNT", "잔액이 충분하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

}
