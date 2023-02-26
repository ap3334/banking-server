package com.example.bankingserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    private int balance;

    @Builder
    public Account(Users user, int balance) {
        this.user = user;
        this.balance = balance;
    }

    public void add(int money) {
        balance += money;
    }

    public void subtract(int money) {
        balance -= money;
    }

}
