package com.example.bankingserver.service;

import com.example.bankingserver.core.friendship.entity.Friendship;
import com.example.bankingserver.core.friendship.repository.FriendshipRepository;
import com.example.bankingserver.core.friendship.service.FriendshipService;
import com.example.bankingserver.core.user.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @InjectMocks
    private FriendshipService friendshipService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    public void 친구추가() {

        // given
        Users user1 = new Users("test1", "1234");
        Users user2 = new Users("test2", "1234");

        Friendship friendship = new Friendship(user1, user2);

        Long fakeFriendshipId = 1L;
        ReflectionTestUtils.setField(friendship, "id", fakeFriendshipId);

        Long fakeUser1Id = 1L;
        Long fakeUser2Id = 2L;
        ReflectionTestUtils.setField(user1, "id", fakeUser1Id);
        ReflectionTestUtils.setField(user2, "id", fakeUser2Id);

        // mocking
        given(friendshipRepository.save(any()))
                .willReturn(friendship);
        given(friendshipRepository.findById(fakeFriendshipId))
                .willReturn(Optional.ofNullable(friendship));

        // when
        Long newFriendshipId = friendshipService.addFriend(user1, user2);

        // then
        Friendship findFriendship = friendshipRepository.findById(newFriendshipId).get();

        assertThat(friendship.getId()).isEqualTo(findFriendship.getId());
        assertThat(friendship.getUser()).isEqualTo(findFriendship.getUser());
        assertThat(friendship.getFriend()).isEqualTo(findFriendship.getFriend());


    }


}