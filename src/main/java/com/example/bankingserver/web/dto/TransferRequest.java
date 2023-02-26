package com.example.bankingserver.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferRequest {

    private Long receiverAccountId;

    private int amount;
}
