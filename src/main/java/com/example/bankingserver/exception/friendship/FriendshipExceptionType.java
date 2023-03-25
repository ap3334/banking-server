package com.example.bankingserver.exception.friendship;

import com.example.bankingserver.exception.global.BasicExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FriendshipExceptionType implements BasicExceptionType {

    NOT_ALLOWED_FRIENDSHIP("NOT_ALLOWED_FRIENDSHIP", "자기 자신과는 친구관계를 맺을 수 없습니다.", HttpStatus.FORBIDDEN),
    NOT_EXIST_FRIENDSHIP("NOT_EXIST_FRIENDSHIP", "친구가 아닌 사용자입니다.", HttpStatus.FORBIDDEN),
    EXIST_FRIENDSHIP("EXIST_FRIENDSHIP", "이미 친구 추가되어 있는 사용자입니다.", HttpStatus.CONFLICT);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

}
