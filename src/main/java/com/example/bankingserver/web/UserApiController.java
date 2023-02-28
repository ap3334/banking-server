package com.example.bankingserver.web;

import com.example.bankingserver.domain.Friendship;
import com.example.bankingserver.domain.Users;
import com.example.bankingserver.exception.ForbiddenException;
import com.example.bankingserver.exception.UsernameDuplicateException;
import com.example.bankingserver.service.FriendshipService;
import com.example.bankingserver.service.UserService;
import com.example.bankingserver.web.dto.UserDto;
import com.example.bankingserver.web.dto.UsernameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping("/user")
    public Long join(@RequestBody UserDto userDto) {

        boolean existUsername = userService.checkUsernameDuplicate(userDto.getUsername());

        if (existUsername) {
            throw new UsernameDuplicateException(userDto.getUsername() + "은/는 이미 존재합니다.");
        }

        return userService.save(userDto);
    }

    @PostMapping("/user/{userId}/friend")
    public Long addFriend(@RequestBody UsernameDto usernameDto, @PathVariable Long userId) {

        Users user = userService.findById(userId);

        Users friend = userService.findByUsername(usernameDto.getUsername());

        if (user.getId().equals(friend.getId())) {
            throw new ForbiddenException("사용자는 자기 자신을 친구로 추가할 수 없습니다.");
        }

        return friendshipService.addFriend(user, friend);
    }

    @GetMapping("user/{userId}/friend")
    public List<UsernameDto> friendList(@PathVariable Long userId) {

        Users user = userService.findById(userId);
        List<Friendship> friendshipList = user.getFriendshipList();

        return friendshipList.stream().map(friendship -> new UsernameDto(friendship.getFriend().getUsername())).collect(Collectors.toList());
    }
}
