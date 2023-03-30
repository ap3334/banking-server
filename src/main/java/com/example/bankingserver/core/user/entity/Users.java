package com.example.bankingserver.core.user.entity;

import com.example.bankingserver.core.friendship.entity.Friendship;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendshipList = new ArrayList<>();

    @Builder
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
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
        map.put("authorities", getAuthorities());

        return map;
    }
}
