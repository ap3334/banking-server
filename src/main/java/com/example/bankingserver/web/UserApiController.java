package com.example.bankingserver.web;

import com.example.bankingserver.service.UserService;
import com.example.bankingserver.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/users")
    public Long join(@RequestBody UserDto userDto) {

        return userService.save(userDto);
    }
}
