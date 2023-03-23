package com.example.bankingserver.service;

import com.example.bankingserver.domain.UserRepository;
import com.example.bankingserver.domain.Users;
import com.example.bankingserver.exception.global.BusinessLogicException;
import com.example.bankingserver.exception.user.UserExceptionType;
import com.example.bankingserver.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        boolean isDuplicate = checkUsernameDuplicate(userDto.getUsername());

        if (isDuplicate) {
            throw new BusinessLogicException(UserExceptionType.DUPLICATE_USERNAME);
        }

        Users users = userDto.toEntity();
        users.passwordEncoding(passwordEncoder.encode(users.getPassword()));

        return userRepository.save(users).getId();
    }

    public Users findById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("해당 id의 사용자는 존재하지 않습니다."));
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 Username을 가진 사용자는 존재하지 않습니다."));
    }

    public boolean checkUsernameDuplicate(String username) {

        return userRepository.findByUsername(username).isPresent();
    }
}
