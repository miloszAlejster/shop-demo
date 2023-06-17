package com.pb.service;

import com.pb.dto.UserDto;
import com.pb.model.User;
import com.pb.repository.UserRepository;
import com.pb.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_CreateUser_ReturnUserDto() {
        User user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto savedUser = userService.createUser(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(1L);
        Assertions.assertThat(savedUser.getFirstname()).isEqualTo("John");
        Assertions.assertThat(savedUser.getLastname()).isEqualTo("Doe");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void UserService_FindAllUsers_ReturnsUserDtoList() {
        List<User> userList = Mockito.mock(List.class);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> savedUsers = userService.findAllUsers();

        Assertions.assertThat(savedUsers).isNotNull();
    }

    @Test
    public void UserService_GetUserById_ReturnsUserDto() {
        User user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        UserDto savedUser = userService.getUserById(1L);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(1L);
        Assertions.assertThat(savedUser.getFirstname()).isEqualTo("John");
        Assertions.assertThat(savedUser.getLastname()).isEqualTo("Doe");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void UserService_DeleteUser() {
        Long userId = 1L;

        assertAll(() -> userService.deleteUser(userId));
    }
}
