package com.example.bankingserver.global.exception;

import com.example.bankingserver.global.dto.RsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<RsData> handleException(BusinessLogicException e) {

        return new ResponseEntity<>(RsData.of(e.getBasicExceptionType().getErrorCode(), e.getBasicExceptionType().getMessage()),
                e.getBasicExceptionType().getHttpStatus());
    }
}
