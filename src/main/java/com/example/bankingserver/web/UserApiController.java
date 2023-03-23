package com.example.bankingserver.web;

import com.example.bankingserver.base.dto.RsData;
import com.example.bankingserver.domain.Friendship;
import com.example.bankingserver.domain.Users;
import com.example.bankingserver.service.FriendshipService;
import com.example.bankingserver.service.UserService;
import com.example.bankingserver.web.dto.UserDto;
import com.example.bankingserver.web.dto.UsernameDto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/user")
    public ResponseEntity<RsData> join(@RequestBody UserDto userDto) {

        userService.save(userDto);

        return new ResponseEntity<>(RsData.of("SUCCESS", "회원가입이 완료되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/friend")
    public ResponseEntity<RsData> addFriend(@RequestBody UsernameDto usernameDto, @PathVariable Long userId) {

        Users user = userService.findById(userId);

        Users friend = userService.findByUsername(usernameDto.getUsername());

        friendshipService.addFriend(user, friend);

        return new ResponseEntity<>(RsData.of("SUCCESS", "친구 추가가 완료되었습니다."), HttpStatus.OK);
    }

    @GetMapping("user/{userId}/friend")
    public ResponseEntity<RsData> friendList(@PathVariable Long userId) {

        Users user = userService.findById(userId);
        List<Friendship> friendshipList = user.getFriendshipList();

        List<UsernameDto> friendList = friendshipList.stream().map(friendship -> new UsernameDto(friendship.getFriend().getUsername())).collect(Collectors.toList());

        return new ResponseEntity<>(RsData.of("SUCCESS", "친구목록 조회 결과입니다.", friendList), HttpStatus.OK);
    }
}
