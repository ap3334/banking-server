package com.example.bankingserver.core.user.dto.request;

import com.example.bankingserver.core.user.entity.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {

    private String username;
    private String password;

    @Builder
    public UserLoginRequestDto(String username, String password) {
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
