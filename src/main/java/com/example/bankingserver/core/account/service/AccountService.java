package com.example.bankingserver.core.account.service;

import com.example.bankingserver.core.account.entity.Account;
import com.example.bankingserver.core.transaction.entity.Transaction;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.account.repository.AccountRepository;
import com.example.bankingserver.core.transaction.repository.TransactionRepository;
import com.example.bankingserver.core.account.exception.AccountExceptionType;
import com.example.bankingserver.core.friendship.exception.FriendshipExceptionType;
import com.example.bankingserver.global.exception.BusinessLogicException;
import com.example.bankingserver.core.friendship.service.FriendshipService;
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
                .orElseThrow(() -> new BusinessLogicException(AccountExceptionType.NOT_FOUND_ACCOUNT));
        Account recipientAccount = accountRepository.findByIdWithPessimisticLock(recipientAccountId)
                .orElseThrow(() -> new BusinessLogicException(AccountExceptionType.NOT_FOUND_ACCOUNT));

        Users sender = senderAccount.getUser();
        Users recipient = recipientAccount.getUser();

        boolean isFriend = friendshipService.checkFriendship(sender.getId(), recipient.getId());

        if (!isFriend) {
            throw new BusinessLogicException(FriendshipExceptionType.NOT_EXIST_FRIENDSHIP);
        }

        if (senderAccount.getBalance() < amount) {
            throw new BusinessLogicException(AccountExceptionType.NOT_ENOUGH_AMOUNT);
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

        Account account = accountRepository.findById(accountId).orElseThrow(() ->  new BusinessLogicException(AccountExceptionType.NOT_FOUND_ACCOUNT));

        return account.getBalance();
    }

}
