package com.example.bankingserver.service;

import com.example.bankingserver.domain.Users;
import com.example.bankingserver.domain.UserRepository;
import com.example.bankingserver.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception {

        // given
        UserDto userDto = UserDto.builder()
                .username("test")
                .password("{bcrypt}1234")
                .build();

        Users user = userDto.toEntity();

        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);

        // mocking
        given(userRepository.save(any()))
                .willReturn(user);
        given(userRepository.findById(fakeUserId))
                .willReturn(Optional.ofNullable(user));
        given(passwordEncoder.encode(any()))
                .willReturn("encoderPassword");

        // when
        Long newUserId = userService.save(userDto);

        // then
        Users findUser = userRepository.findById(newUserId).get();

        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
//        assertThat(user.getPassword()).equals(findUser.getPassword());

    }

}