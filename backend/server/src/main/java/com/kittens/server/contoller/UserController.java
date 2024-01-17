package com.kittens.server.contoller;

import com.kittens.server.dto.UserNicknameDto;
import com.kittens.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController
{
    private final UserService userService;


    @GetMapping("/{userId}")
    public UserNicknameDto readUserById(@PathVariable Long userId)
    {
        return userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User с таким id: " + userId + "не найден."));
    }
}
