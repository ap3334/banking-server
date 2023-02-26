package com.example.bankingserver.service;

import com.example.bankingserver.domain.Account;
import com.example.bankingserver.domain.AccountRepository;
import com.example.bankingserver.domain.Transaction;
import com.example.bankingserver.domain.TransactionRepository;
import com.example.bankingserver.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public int transferAccount(Long senderAccountId, Long recipientAccountId, int amount) {

        Account senderAccount = accountRepository.findByUserId(senderAccountId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 발신자의 계좌는 존재하지 않습니다."));
        Account recipientAccount = accountRepository.findByUserId(recipientAccountId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 수신자의 계좌는 존재하지 않습니다."));

        if (senderAccount.getBalance() < amount) {
            throw new BadRequestException("잔액이 충분하지 않습니다.");
        }

        senderAccount.subtract(amount);
        recipientAccount.add(amount);

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .recipientAccount(recipientAccount)
                .amount(amount)
                .build();

        transactionRepository.save(transaction);

        return senderAccount.getBalance();

    }

}
