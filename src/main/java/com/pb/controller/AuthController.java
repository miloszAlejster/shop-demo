package com.pb.controller;

import com.pb.model.Role;
import com.pb.model.User;
import com.pb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public String processRegister(User user, Model model) {
        // user already exists
        if(userService.checkEmail(user.getEmail())) {
            model.addAttribute("message", "That email wash already used");
            return "register";
        }
        // TODO: more validations
        user.setRole(Role.USER);
        userService.createUser(user);
        model.addAttribute("message", "User created successfully");

        return "login";
    }
    @PostMapping("/loginUser")
    public String processLogin(Model model) {

        return "home";
    }
}
