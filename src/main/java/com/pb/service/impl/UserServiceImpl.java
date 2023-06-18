package com.pb.service.impl;

import com.pb.dto.UserDto;
import com.pb.model.Role;
import com.pb.model.SecurityUser;
import com.pb.model.User;
import com.pb.repository.UserRepository;
import com.pb.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(User newUser) {
        User createdUser = userRepository.save(newUser);
        return mapToUserDto(createdUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        User foundUser = userRepository.findById(id).get();
        UserDto userDto = mapToUserDto(foundUser);
        return userDto;
    }

    @Override
    public void updateUser(UserDto userDto) {
//        User user = maptoUser(userDto);
//        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setFirstname(userDto.getFirstname());
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserFirstName(Long id, String newFirstName) {
//        User user = userRepository.findById(id).get();
//        user.setFirstname(newFirstName);
//        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstname(newFirstName);
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserLastName(Long id, String newLastName) {
//        User user = userRepository.findById(id).get();
//        user.setLastname(newLastName);
//        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastname(newLastName);
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserEmail(Long id, String newEmail) {
//        User user = userRepository.findById(id).get();
//        user.setEmail(newEmail);
//        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(newEmail);
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserPassword(Long id, String newPassword) {
//        User user = userRepository.findById(id).get();
//        user.setPassword(newPassword);
//        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if(foundUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto userDto = mapToUserDto(foundUser.get());
        return Optional.ofNullable(userDto);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .orders(user.getOrders())
                .password(user.getPassword())
                .role(user.getRole().getValue())
                .build();
        return userDto;
    }

    private User maptoUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .orders(userDto.getOrders())
                .password(userDto.getPassword())
                .role(Role.valueOf(userDto.getRole()))
                .build();
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByEmail(username);
        return foundUser
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found" + username));
    }
}
