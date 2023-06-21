package com.pb.service;

import com.pb.dto.UserDto;
import com.pb.model.Order;
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

import java.util.ArrayList;
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
                .orders(new ArrayList<Order>())
                .build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto savedUser = userService.createUser(user);

        verify(userRepository, times(1)).save(user);
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

        verify(userRepository, times(1)).findAll();
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
                .orders(new ArrayList<Order>())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        UserDto savedUser = userService.getUserById(1L);

        verify(userRepository, times(1)).findById(1L);
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

    @Test
    public void UserService_CheckEmail_ReturnsTrue() {
        String email = "existing@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean found = userService.checkEmail(email);

        verify(userRepository, times(1)).existsByEmail(email);
        Assertions.assertThat(found).isTrue();
    }

    @Test
    public void UserService_CheckEmail_ReturnsFalse() {
        String email = "nonexisting@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean found = userService.checkEmail(email);

        verify(userRepository, times(1)).existsByEmail(email);
        Assertions.assertThat(found).isFalse();
    }

    @Test
    public void UserService_UpdateUserEmail_UserExists_EmailUpdated() {
        Long userId = 1L;
        String newEmail = "newemail@example.com";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("oldemail@example.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail(newEmail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        userService.updateUserEmail(userId, newEmail);

        verify(userRepository).findById(userId);
        verify(userRepository).save(updatedUser);
        Assertions.assertThat(newEmail).isEqualTo(existingUser.getEmail());
    }

    @Test
    public void UserService_UpdateUserEmail_UserDontExists_NothingUpdated() {
        Long userId = 1L;
        String newEmail = "newemail@example.com";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUserEmail(userId, newEmail);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void UserService_UpdateUserPassword_UserExists_PasswordUpdated() {
        Long userId = 1L;
        String newPassword = "newpassword";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("oldpassword");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setPassword(newPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        userService.updateUserPassword(userId, newPassword);

        verify(userRepository).findById(userId);
        verify(userRepository).save(updatedUser);
        Assertions.assertThat(newPassword).isEqualTo(existingUser.getPassword());
    }

    @Test
    public void UserService_UpdateUserPassword_UserDontExists_NothingUpdated() {
        Long userId = 1L;
        String newPassword = "newpassword";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUserPassword(userId, newPassword);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void UserService_UpdateUserFirstname_UserExists_FirstnameUpdated() {
        Long userId = 1L;
        String newFirstname = "newfistname";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstname("oldfirstname");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstname(newFirstname);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        userService.updateUserFirstName(userId, newFirstname);

        verify(userRepository).findById(userId);
        verify(userRepository).save(updatedUser);
        Assertions.assertThat(newFirstname).isEqualTo(existingUser.getFirstname());
    }

    @Test
    public void UserService_UpdateUserFirstname_UserDontExists_NothingUpdated() {
        Long userId = 1L;
        String newFirstname = "newfistname";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUserFirstName(userId, newFirstname);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void UserService_UpdateUserLastname_UserExists_LastnameUpdated() {
        Long userId = 1L;
        String newLastname = "newlastname";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setLastname("oldlastname");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setLastname(newLastname);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        userService.updateUserLastName(userId, newLastname);

        verify(userRepository).findById(userId);
        verify(userRepository).save(updatedUser);
        Assertions.assertThat(newLastname).isEqualTo(existingUser.getLastname());
    }

    @Test
    public void UserService_UpdateUserLastname_UserDontExists_NothingUpdated() {
        Long userId = 1L;
        String newLastname = "newlastname";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUserLastName(userId, newLastname);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void UserService_UpdateUser_UserExists_UserUpdated() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .orders(new ArrayList<>())
                .build();

        User existngUser = User.builder()
                .id(1L)
                .firstname("Bob")
                .lastname("Smith")
                .email("john.smith@example.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existngUser));

        userService.updateUser(userDto);

        verify(userRepository).save(existngUser);

        Assertions.assertThat(userDto.getFirstname()).isEqualTo(existngUser.getFirstname());
        Assertions.assertThat(userDto.getLastname()).isEqualTo(existngUser.getLastname());
        Assertions.assertThat(userDto.getEmail()).isEqualTo(existngUser.getEmail());
    }

    @Test
    public void UserService_UpdateUser_UserDontExists_NothingUpdated() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .orders(new ArrayList<>())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.updateUser(userDto);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void UserService_FindByEmail_UserFound_ReturnsOptionalUserDto() {
        User user = User.builder()
                .id(1L)
                .email("example@example.com")
                .orders(new ArrayList<>())
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<UserDto> optionalUserDto = userService.findByEmail(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        Assertions.assertThat(user.getEmail()).isEqualTo(optionalUserDto.get().getEmail());
    }

    @Test
    public void UserService_FindByEmail_UserNotFound_ReturnsOptionalEmpty() {
        String email = "example@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<UserDto> optionalUserDto = userService.findByEmail(email);

        verify(userRepository, times(1)).findByEmail(email);
        Assertions.assertThat(optionalUserDto).isEqualTo(Optional.empty());
    }
}
