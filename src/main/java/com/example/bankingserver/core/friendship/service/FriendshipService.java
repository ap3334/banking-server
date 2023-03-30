package com.example.bankingserver.core.friendship.service;

import com.example.bankingserver.core.friendship.entity.Friendship;
import com.example.bankingserver.core.friendship.repository.FriendshipRepository;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.friendship.exception.FriendshipExceptionType;
import com.example.bankingserver.global.exception.BusinessLogicException;
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
