package com.example.bankingserver.service;

import com.example.bankingserver.domain.Friendship;
import com.example.bankingserver.domain.FriendshipRepository;
import com.example.bankingserver.domain.Users;
import com.example.bankingserver.exception.friendship.FriendshipExceptionType;
import com.example.bankingserver.exception.global.BusinessLogicException;
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

        if (user.getId().equals(friend.getId())) {
            throw new BusinessLogicException(FriendshipExceptionType.NOT_ALLOWED_FRIENDSHIP);
        }

        Optional<Friendship> friendshipOptional = friendshipRepository.findByUserIdAndFriendId(user.getId(), friend.getId());

        if (friendshipOptional.isPresent()) {
            throw new BusinessLogicException(FriendshipExceptionType.EXIST_FRIENDSHIP);
        }

        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .build();

        return friendshipRepository.save(friendship).getId();
    }

    public boolean checkFriendship(Long senderId, Long recipientId) {

        return friendshipRepository.findByUserIdAndFriendId(senderId, recipientId).isPresent();
    }
}
