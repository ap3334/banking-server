package com.example.bankingserver.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferRequest {

    private Long receiverAccountId;

    private int amount;

    @Builder
    public TransferRequest(Long receiverAccountId, int amount) {
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }
}
