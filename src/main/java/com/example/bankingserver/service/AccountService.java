package com.example.bankingserver.service;

import com.example.bankingserver.domain.Account;
import com.example.bankingserver.domain.AccountRepository;
import com.example.bankingserver.domain.Transaction;
import com.example.bankingserver.domain.TransactionRepository;
import com.example.bankingserver.exception.AccountNotFoundException;
import com.example.bankingserver.exception.BadRequestException;
import com.example.bankingserver.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final FriendshipService friendshipService;

    public int transferAccount(Long senderAccountId, Long recipientAccountId, int amount) {

        Account senderAccount = accountRepository.findByIdWithPessimisticLock(senderAccountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 계좌는 존재하지 않습니다."));
        Account recipientAccount = accountRepository.findByIdWithPessimisticLock(recipientAccountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 계좌는 존재하지 않습니다."));

        boolean isFriend = friendshipService.checkFriendship(senderAccount.getUser().getId(), recipientAccount.getUser().getId());

        if (!isFriend) {
            throw new ForbiddenException("친구관계가 아닌 사용자에게는 계좌이체를 할 수 없습니다.");
        }

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

    @Transactional(readOnly = true)
    public int searchAccount(Long accountId) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("해당 계좌는 존재하지 않습니다."));

        return account.getBalance();
    }

}
