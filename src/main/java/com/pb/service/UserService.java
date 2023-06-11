package com.pb.service;

import com.pb.dto.UserDto;
import com.pb.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
    UserDto createUser(User newUser);
    void deleteUser(Long id);
    UserDto getUserById(Long id);
    void updateUser(UserDto userDto);
    void updateUserFirstName(Long id, String newFirstName);
    void updateUserLastName(Long id, String newLastName);
    void updateUserEmail(Long id, String newEmail);
    void updateUserPassword(Long id, String newPassword);
    boolean checkEmail(String email);
}
