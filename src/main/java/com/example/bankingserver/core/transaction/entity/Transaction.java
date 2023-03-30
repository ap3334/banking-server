package com.example.bankingserver.core.transaction.entity;

import com.example.bankingserver.core.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account recipientAccount;

    private int amount;

    @Builder
    public Transaction(Account senderAccount, Account recipientAccount, int amount) {
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
    }
}
