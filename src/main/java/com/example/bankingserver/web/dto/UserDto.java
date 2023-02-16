package com.example.bankingserver.web.dto;

import com.example.bankingserver.domain.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private String username;
    private String password;

    @Builder
    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users toEntity() {
        return Users.builder()
                .username(username)
                .password(password)
                .build();
    }
}
