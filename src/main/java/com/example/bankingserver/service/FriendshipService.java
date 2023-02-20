package com.example.bankingserver.service;

import com.example.bankingserver.domain.Friendship;
import com.example.bankingserver.domain.FriendshipRepository;
import com.example.bankingserver.domain.Users;
import com.example.bankingserver.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public Long addFriend(Users user, Users friend) {

        Optional<Friendship> friendshipOptional = friendshipRepository.findByUserIdAndFriendId(user.getId(), friend.getId());

        if (friendshipOptional.isPresent()) {
            throw new BadRequestException("이미 친구 추가되어있는 사용자입니다.");
        }

        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .build();

        friendshipRepository.save(friendship);

        return friend.getId();
    }

}
