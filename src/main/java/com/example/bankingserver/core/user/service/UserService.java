package com.example.bankingserver.core.user.service;

import com.example.bankingserver.core.security.jwt.JwtProvider;
import com.example.bankingserver.core.user.dto.request.UserJoinRequestDto;
import com.example.bankingserver.core.user.dto.request.UserLoginRequestDto;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.user.exception.UserExceptionType;
import com.example.bankingserver.core.user.repository.UserRepository;
import com.example.bankingserver.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public Long signUp(UserJoinRequestDto userJoinRequestDto) {

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

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BusinessLogicException(UserExceptionType.NOT_FOUND_USER));
    }

    public String generateAccessToken(UserLoginRequestDto dto) {

        Users user = findByUsername__cached(dto.getUsername());

        String accessToken = user.getAccessToken();

        if (!StringUtils.hasLength(accessToken)) {
            accessToken = jwtProvider.generateAccessToken(user.getAccessTokenClaims(), 60 * 30 * 1000L); // 30분
        }

        user.changeAccessToken(accessToken);

        return accessToken;
    }

    public boolean verifyWithWhiteList(Users user, String token) {

        return user.getAccessToken().equals(token);
    }

    @Cacheable(value = "user", key = "#username")
    public Users findByUsername__cached(String username) {

        return findByUsername(username);

    }

    @CacheEvict(value = "user", key = "#user.username")
    public void deleteAccessToken(Users user) {

        user.changeAccessToken("");
    }
}
