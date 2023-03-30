package com.example.bankingserver.core.user.service;

import com.example.bankingserver.core.user.dto.request.UserJoinRequestDto;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.user.exception.UserExceptionType;
import com.example.bankingserver.core.user.repository.UserRepository;
import com.example.bankingserver.global.exception.BusinessLogicException;
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

    public Long save(UserJoinRequestDto userJoinRequestDto) {

        boolean isDuplicate = checkUsernameDuplicate(userJoinRequestDto.getUsername());

        if (isDuplicate) {
            throw new BusinessLogicException(UserExceptionType.DUPLICATE_USERNAME);
        }

        Users users = userJoinRequestDto.toEntity();
        users.passwordEncoding(passwordEncoder.encode(users.getPassword()));

        return userRepository.save(users).getId();
    }

    public Users findById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(UserExceptionType.NOT_FOUND_USER));
    }

    public boolean checkUsernameDuplicate(String username) {

        return userRepository.findByUsername(username).isPresent();
    }
}
