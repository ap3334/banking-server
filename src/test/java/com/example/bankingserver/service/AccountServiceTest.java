package com.example.bankingserver.service;

import com.example.bankingserver.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void 계좌이체_멀티쓰레드_동시성테스트() throws Exception {

        // given
        Users userA = new Users("userA", "1234");
        Users userB = new Users("userB", "1234");
        userRepository.saveAll(List.of(userA, userB));

        Friendship friendship = new Friendship(userA, userB);
        friendshipRepository.save(friendship);


        Account accountA = new Account(userA, 20000);
        Account accountB = new Account(userB, 2000);
        accountRepository.saveAll(List.of(accountA, accountB));

        ExecutorService service = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        // when
        service.execute(() -> {
            accountService.transferAccount(accountA.getId(), accountB.getId(), 1000);
            latch.countDown();
        });

        service.execute(() -> {
            accountService.transferAccount(accountA.getId(), accountB.getId(), 2000);
            latch.countDown();
        });

        latch.await();

        // then
        Account resultA = accountRepository.findByUserId(userA.getId()).get();
        Account resultB = accountRepository.findByUserId(userB.getId()).get();

        assertThat(resultA.getBalance()).isEqualTo(17000);
        assertThat(resultB.getBalance()).isEqualTo(5000);

    }

    @Test
    public void 계좌조회() throws Exception {

        // given
        Users user = new Users("user", "1234");
        userRepository.save(user);

        Account account = new Account(user, 20000);
        accountRepository.save(account);

        // when
        int balance = accountService.searchAccount(account.getId());

        // then
        assertThat(balance).isEqualTo(20000);


    }

}