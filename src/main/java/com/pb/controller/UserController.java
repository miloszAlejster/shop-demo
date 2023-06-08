package com.pb.controller;

import com.pb.model.User;
import com.pb.repository.UserRepository;
import com.pb.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService service;
    UserController (UserService service) {
        this.service = service;
    }
    @GetMapping("/users")
    List<User> all() {
        return service.getAllUsers();
    }
    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }
    @GetMapping("/users/{id}")
    User oneUser(@PathVariable Long id) {
        return service.getUserById(id);
    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
