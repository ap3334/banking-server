package com.example.bankingserver.core.account.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTransferRequestDto {

    private Long receiverAccountId;

    private int amount;

    @Builder
    public AccountTransferRequestDto(Long receiverAccountId, int amount) {
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }
}
