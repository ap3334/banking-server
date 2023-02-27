package com.example.bankingserver.web;

import com.example.bankingserver.service.AccountService;
import com.example.bankingserver.web.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountApiController {

    private final AccountService accountService;

    @PutMapping("/account/{senderAccountId}")
    public int transferAccount(@PathVariable Long senderAccountId, @RequestBody TransferRequest transferRequest) {

        return accountService.transferAccount(senderAccountId, transferRequest.getReceiverAccountId(), transferRequest.getAmount());
    }

    @GetMapping("/account/{accountId}")
    public int searchAccount(@PathVariable Long accountId) {

        return accountService.searchAccount(accountId);
    }

}
