package com.example.bankingserver.web;

import com.example.bankingserver.base.dto.RsData;
import com.example.bankingserver.service.AccountService;
import com.example.bankingserver.web.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountApiController {

    private final AccountService accountService;

    @PutMapping("/account/{senderAccountId}")
    public ResponseEntity<RsData> transferAccount(@PathVariable Long senderAccountId, @RequestBody TransferRequest transferRequest) {

        accountService.transferAccount(senderAccountId, transferRequest.getReceiverAccountId(), transferRequest.getAmount());

        return new ResponseEntity<>(RsData.of("SUCCESS", "계좌이체가 완료되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<RsData> searchAccount(@PathVariable Long accountId) {

        int amount = accountService.searchAccount(accountId);

        return new ResponseEntity<>(RsData.of("SUCCESS", "계좌 잔액 조회가 완료되었습니다.", amount), HttpStatus.OK);
    }

}
