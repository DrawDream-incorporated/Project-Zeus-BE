package com.konada.Konada.controller;

import com.konada.Konada.dto.UserDto;
import com.konada.Konada.entity.User;
import com.konada.Konada.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    public AuthenticationController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/register")
    public User createUser(@RequestBody UserDto userDto){
        return userDetailsService.createUser(UserDto.toUser(userDto));
    }

}
