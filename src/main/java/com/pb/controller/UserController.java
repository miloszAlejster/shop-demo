package com.pb.controller;

import com.pb.dto.UserDto;
import com.pb.model.User;
import com.pb.service.UserService;
import com.pb.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    @Autowired
    public UserController (UserService service) {
        this.service = service;
    }
    @GetMapping("/getAll")
    @ResponseBody
    List<UserDto> allUsers() {
        return service.findAllUsers();
    }
    @PostMapping("/add")
    @ResponseBody
    UserDto newUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }
    @GetMapping("/get")
    @ResponseBody
    UserDto oneUser(@RequestParam("id") Long id) {
        return service.getUserById(id);
    }
    @DeleteMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    void deleteUser(@RequestParam("id") Long id) {
        service.deleteUser(id);
    }
    @PostMapping(value = "/edit", consumes = "application/x-www-form-urlencoded")
    public String updateUser(UserDto userDto, HttpServletRequest request, Model model) {
        service.updateUser(userDto);
        // TODO: temporary solution
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
