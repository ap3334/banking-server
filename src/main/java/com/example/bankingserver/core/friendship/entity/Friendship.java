package com.example.bankingserver.core.friendship.entity;

import com.example.bankingserver.core.user.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users friend;

    @Builder
    public Friendship(Users user, Users friend) {
        this.user = user;
        this.friend = friend;

        this.user.getFriendshipList().add(this);
    }
}
