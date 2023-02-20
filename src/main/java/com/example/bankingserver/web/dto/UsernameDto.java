package com.example.bankingserver.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameDto {

    private String username;

    @Builder
    public UsernameDto(String username) {
        this.username = username;
    }
}
