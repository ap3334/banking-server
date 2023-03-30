package com.example.bankingserver.core.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameResponseDto {

    private String username;

    @Builder
    public UsernameResponseDto(String username) {
        this.username = username;
    }
}
