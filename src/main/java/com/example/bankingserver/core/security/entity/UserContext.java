package com.example.bankingserver.core.security.entity;

import com.example.bankingserver.core.user.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserContext extends User {

    private final long id;
    private final String username;
    private final Set<GrantedAuthority> authorities;

    public UserContext(Users user) {
        super(user.getUsername(), "", user.getAuthorities());

        id = user.getId();
        username = user.getUsername();
        authorities = user.getAuthorities().stream().collect(Collectors.toSet());
    }
}
