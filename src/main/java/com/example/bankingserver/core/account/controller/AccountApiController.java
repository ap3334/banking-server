package com.example.bankingserver.core.account.controller;

import com.example.bankingserver.global.dto.RsData;
import com.example.bankingserver.core.account.service.AccountService;
import com.example.bankingserver.core.account.dto.request.AccountTransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountApiController {

    private final AccountService accountService;

    @PutMapping("/account/{senderAccountId}")
    public ResponseEntity<RsData> transferAccount(@PathVariable Long senderAccountId, @RequestBody AccountTransferRequestDto accountTransferRequestDto) {

        accountService.transferAccount(senderAccountId, accountTransferRequestDto.getReceiverAccountId(), accountTransferRequestDto.getAmount());

        return new ResponseEntity<>(RsData.of("SUCCESS", "계좌이체가 완료되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<RsData> searchAccount(@PathVariable Long accountId) {

        int amount = accountService.searchAccount(accountId);

        return new ResponseEntity<>(RsData.of("SUCCESS", "계좌 잔액 조회가 완료되었습니다.", amount), HttpStatus.OK);
    }

}
