package com.example.bankingserver.web;

import com.example.bankingserver.core.friendship.entity.Friendship;
import com.example.bankingserver.core.friendship.repository.FriendshipRepository;
import com.example.bankingserver.core.user.repository.UserRepository;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.user.dto.request.UserJoinRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

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
        userRepository.deleteAll();
    }

    @Test
    public void 회원가입_ApiTest() throws Exception {

        // given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .username("test")
                .password("1234")
                .build();

        String url = "http://localhost:" + port + "/user";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userJoinRequestDto)))
                .andExpect(status().isOk());

        // then
        List<Users> all = userRepository.findAll();
        assertThat(all.get(0).getUsername()).isEqualTo(userJoinRequestDto.getUsername());

    }

    @Test
    public void 회원가입_ApiTest_실패_중복Username가입() throws Exception {

        // given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .username("test")
                .password("1234")
                .build();

        String url = "http://localhost:" + port + "/user";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userJoinRequestDto)))
                .andExpect(status().isOk());

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userJoinRequestDto)))
                .andExpect(status().isConflict());

    }

    @Test
    public void 친구추가_ApiTest_성공() throws Exception {

        // given
        Users user1 = userRepository.save(new Users("test1", "1234"));
        Users user2 = userRepository.save(new Users("test2", "1234"));
        Users user3 = userRepository.save(new Users("test3", "1234"));

        String url1 = "http://localhost:" + port + "/user/" + user1.getId() + "/friend/" + user2.getId();
        String url2 = "http://localhost:" + port + "/user/" + user1.getId() + "/friend/" + user3.getId();


        // when
        mvc.perform(post(url1))
                .andExpect(status().isOk());

        mvc.perform(post(url2))
                .andExpect(status().isOk());

        // then
        List<Friendship> friendshipList = userRepository.findById(user1.getId()).get().getFriendshipList();
        assertThat(friendshipList.size()).isEqualTo(2);
        assertThat(friendshipList.get(0).getFriend().getUsername()).isEqualTo("test2");
        assertThat(friendshipList.get(1).getFriend().getUsername()).isEqualTo("test3");

    }

    @Test
    public void 친구추가_ApiTest_실패_사용자가본인을친구추가할때() throws Exception {

        // given
        Users user1 = userRepository.save(new Users("test1", "1234"));

        String url = "http://localhost:" + port + "/user/" + user1.getId() + "/friend/" + user1.getId();

        // when
        mvc.perform(post(url))
                .andExpect(status().isForbidden());

        // then
        List<Friendship> friendshipList = userRepository.findById(user1.getId()).get().getFriendshipList();
        assertThat(friendshipList.size()).isEqualTo(0);

    }

    @Test
    public void 친구추가_ApiTest_실패_존재하지않는사용자친구추가할때() throws Exception {

        // given
        Users user = userRepository.save(new Users("test1", "1234"));

        String url = "http://localhost:" + port + "/user/" + user.getId() + "/friend/" + user.getId() + 1;

        // when
        mvc.perform(post(url))
                .andExpect(status().isNotFound());

        // then
        List<Friendship> friendshipList = userRepository.findById(user.getId()).get().getFriendshipList();
        assertThat(friendshipList.size()).isEqualTo(0);

    }

    @Test
    public void 친구추가_ApiTest_실패_중복친구추가할때() throws Exception {

        // given
        Users user1 = userRepository.save(new Users("test1", "1234"));
        Users user2 = userRepository.save(new Users("test2", "1234"));

        String url = "http://localhost:" + port + "/user/" + user1.getId() + "/friend/" + user2.getId();

        // when
        mvc.perform(post(url))
                .andExpect(status().isOk());

        mvc.perform(post(url))
                .andExpect(status().isConflict());

        // then
        List<Friendship> friendshipList = userRepository.findById(user1.getId()).get().getFriendshipList();
        assertThat(friendshipList.size()).isEqualTo(1);

    }

    @Test
    public void 친구목록조회_ApiTest_성공() throws Exception {

        // given
        Users user1 = userRepository.save(new Users("test1", "1234"));
        Users user2 = userRepository.save(new Users("test2", "1234"));
        Users user3 = userRepository.save(new Users("test3", "1234"));

        friendshipRepository.save(new Friendship(user1, user2));
        friendshipRepository.save(new Friendship(user1, user3));

        String url = "http://localhost:" + port + "/user/" + user1.getId() + "/friend";

        // when, then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].username").value("test2"))
                .andExpect(jsonPath("$.data[1].username").value("test3"));

    }

    @Test
    public void 친구목록조회_ApiTest_실패_존재하지않는사용자조회() throws Exception {

        // given
        String url = "http://localhost:" + port + "/user/2/friend";

        // when, then
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError());

    }
}