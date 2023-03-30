package com.example.bankingserver.core.user.entity;

import com.example.bankingserver.core.friendship.entity.Friendship;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendshipList = new ArrayList<>();

    @Builder
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public void changeAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void passwordEncoding(String encodingPassword) {
        password = encodingPassword;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return authorities;
    }

    public Map<String, Object> getAccessTokenClaims() {

        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("username", getUsername());

        return map;
    }

}
