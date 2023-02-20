package com.example.bankingserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendshipList = new ArrayList<>();

    @Builder
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void passwordEncoding(String encodingPassword) {
        password = encodingPassword;
    }
}
