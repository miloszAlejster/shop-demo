package com.pb.controller;

import com.pb.dto.UserDto;
import com.pb.model.Role;
import com.pb.model.User;
import com.pb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public String processRegister(User user, Model model) {
        // user already exists
        boolean isUserPresent = userService.checkEmail(user.getEmail());
        if(isUserPresent) {
            return "redirect:/register?error=true";
        }
        // TODO: more validations
        user.setRole(Role.USER);
        userService.createUser(user);
        model.addAttribute("message", "User created successfully");
        return "redirect:/login";
    }
}
