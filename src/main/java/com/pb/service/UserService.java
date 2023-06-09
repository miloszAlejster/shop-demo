package com.pb.service;

import com.pb.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
    void createUser(UserDto userDto);
    void deleteUser(Long id);
    UserDto getUserById(Long id);
    void updateUser(UserDto userDto);
    void updateUserFirstName(Long id, String newFirstName);
    void updateUserLastName(Long id, String newLastName);
    void updateUserEmail(Long id, String newEmail);
    void updateUserPassword(Long id, String newPassword);
}
