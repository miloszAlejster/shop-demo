package com.pb.controller;

import com.pb.dto.RegisterDto;
import com.pb.dto.UserDto;
import com.pb.model.User;
import com.pb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute RegisterDto registerDto) {
        if(userService.checkEmail(registerDto.getEmail())) {
            return "register";
        }
        else {
            User user = new User();
            user.setFirstname(registerDto.getFirstname());
            user.setLastname(registerDto.getLastname());
            user.setEmail(registerDto.getEmail());
            user.setPassword(registerDto.getPassword());

            UserDto userDto = userService.createUser(user);
        }

        return "register";
    }
}
