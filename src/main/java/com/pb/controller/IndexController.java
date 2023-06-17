package com.pb.controller;

import com.pb.dto.UserDto;
import com.pb.model.User;
import com.pb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {
    private final UserService userService;
    @Autowired
    public IndexController (UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    String home(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("title", "Shop");
        model.addAttribute("users", users);
        return "home";
    }
    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
