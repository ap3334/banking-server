package com.example.bankingserver.service;

import com.example.bankingserver.domain.Users;
import com.example.bankingserver.domain.UserRepository;
import com.example.bankingserver.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Long save(UserDto userDto) {

        Users users = userDto.toEntity();
        users.passwordEncoding(passwordEncoder.encode(users.getPassword()));

        return userRepository.save(users).getId();
    }
}
