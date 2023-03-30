package com.example.bankingserver.web;

import com.example.bankingserver.core.account.entity.Account;
import com.example.bankingserver.core.friendship.entity.Friendship;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.account.repository.AccountRepository;
import com.example.bankingserver.core.friendship.repository.FriendshipRepository;
import com.example.bankingserver.core.user.repository.UserRepository;
import com.example.bankingserver.core.account.dto.request.AccountTransferRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void 계좌이체_ApiTest_성공() throws Exception {

        // given
        Users userA = new Users("userA", "1234");
        Users userB = new Users("userB", "1234");
        userRepository.saveAll(List.of(userA, userB));

        Friendship friendship = new Friendship(userA, userB);
        friendshipRepository.save(friendship);

        Account accountA = new Account(userA, 20000);
        Account accountB = new Account(userB, 1000);
        accountRepository.saveAll(List.of(accountA, accountB));

        AccountTransferRequestDto request = AccountTransferRequestDto.builder()
                .receiverAccountId(accountB.getId())
                .amount(2000)
                .build();

        String url = "http://localhost:" + port + "/account/" + accountA.getId();

        // when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        Account resultA = accountRepository.findById(accountA.getId()).get();
        Account resultB = accountRepository.findById(accountB.getId()).get();

        assertThat(resultA.getBalance()).isEqualTo(18000);
        assertThat(resultB.getBalance()).isEqualTo(3000);

    }

    @Test
    public void 계좌조회_ApiTest_성공() throws Exception {

        // given
        Users userA = new Users("userA", "1234");
        userRepository.save(userA);

        Account accountA = new Account(userA, 20000);
        accountRepository.save(accountA);

        String url = "http://localhost:" + port + "/account/" + accountA.getId();

        // when, then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(20000));

    }


}