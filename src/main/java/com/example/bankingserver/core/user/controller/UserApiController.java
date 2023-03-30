package com.example.bankingserver.core.user.controller;

import com.example.bankingserver.core.friendship.entity.Friendship;
import com.example.bankingserver.core.friendship.service.FriendshipService;
import com.example.bankingserver.core.user.dto.request.UserJoinRequestDto;
import com.example.bankingserver.core.user.dto.request.UserLoginRequestDto;
import com.example.bankingserver.core.user.dto.response.UsernameResponseDto;
import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.user.service.UserService;
import com.example.bankingserver.global.dto.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping("/user/auth/new")
    public ResponseEntity<RsData> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {

        userService.save(userJoinRequestDto);

        return new ResponseEntity<>(RsData.of("SUCCESS", "회원가입이 완료되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/user/auth")
    public ResponseEntity<RsData> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {

        String accessToken = userService.generationAccessToken(userLoginRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", accessToken);

        return new ResponseEntity<>(RsData.of("SUCCESS", "로그인이 완료되었습니다."), headers, HttpStatus.OK);

    }

    @PostMapping("/user/{userId}/friend/{friendId}")
    public ResponseEntity<RsData> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {

        Users user = userService.findById(userId);

        Users friend = userService.findById(friendId);

        friendshipService.addFriend(user, friend);

        return new ResponseEntity<>(RsData.of("SUCCESS", "친구 추가가 완료되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/friend")
    public ResponseEntity<RsData> friendList(@PathVariable Long userId) {

        Users user = userService.findById(userId);
        List<Friendship> friendshipList = user.getFriendshipList();

        List<UsernameResponseDto> friendList = friendshipList.stream().map(friendship -> new UsernameResponseDto(friendship.getFriend().getUsername())).collect(Collectors.toList());

        return new ResponseEntity<>(RsData.of("SUCCESS", "친구목록 조회 결과입니다.", friendList), HttpStatus.OK);
    }
}
