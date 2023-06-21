package com.pb.service.impl;

import com.pb.dto.OrderDto;
import com.pb.dto.UserDto;
import com.pb.model.Role;
import com.pb.model.SecurityUser;
import com.pb.model.User;
import com.pb.repository.UserRepository;
import com.pb.service.UserService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("All users found");
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(User newUser) {
        User createdUser = userRepository.save(newUser);
        logger.info("New user created: " + createdUser.toString());
        return new UserDto(createdUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        logger.info("User with id: " + id + " deleted.");
    }

    @Override
    public UserDto getUserById(Long id) {
        User foundUser = userRepository.findById(id).get();
        UserDto userDto = new UserDto(foundUser);
        logger.info("User with id: " + id + " found.");
        return userDto;
    }

    @Override
    public void updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            logger.info("User with id: " + userDto.getId() + " found.");
            User user = optionalUser.get();
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setFirstname(userDto.getFirstname());
            userRepository.save(user);
            logger.info("User with id: " + userDto.getId() + " updated.");
        }
        else {
            logger.error("User with id: " + userDto.getId() + " not found.");
        }
    }

    @Override
    public void updateUserFirstName(Long id, String newFirstName) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("User with id: " + id + " found.");
            User user = optionalUser.get();
            user.setFirstname(newFirstName);
            userRepository.save(user);
            logger.info("First name of user with id: " + id + "updated. New firstname: " + newFirstName);
        }
        else {
            logger.error("User with id: " + id + " not found.");
        }
    }

    @Override
    public void updateUserLastName(Long id, String newLastName) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("User with id: " + id + " found.");
            User user = optionalUser.get();
            user.setLastname(newLastName);
            userRepository.save(user);
            logger.info("Lastname of user with id: " + id + "updated. New lastname: " + newLastName);
        }
        else {
            logger.error("User with id: " + id + " not found.");
        }
    }

    @Override
    public void updateUserEmail(Long id, String newEmail) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("User with id: " + id + " found.");
            User user = optionalUser.get();
            user.setEmail(newEmail);
            userRepository.save(user);
            logger.info("Email of user with id: " + id + "updated. New email: " + newEmail);
        }
        else {
            logger.error("User with id: " + id + " not found.");
        }
    }

    @Override
    public void updateUserPassword(Long id, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("User with id: " + id + " found.");
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            logger.info("Password of user with id: " + id + "updated.");
        }
        else {
            logger.error("User with id: " + id + " not found.");
        }
    }

    @Override
    public boolean checkEmail(String email) {
        boolean found = userRepository.existsByEmail(email);
        if (found) {
            logger.error("Given email exists. Email: " + email);
        }
        else {
            logger.info("Given email does not exists. Email: " + email);
        }
        return found;
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if(foundUser.isEmpty()) {
            logger.error("User with given email does not exists. Email: " + email);
            return Optional.empty();
        }
        UserDto userDto = new UserDto(foundUser.get());
        logger.error("User with given email exists. Email: " + email);
        return Optional.ofNullable(userDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByEmail(username);
        return foundUser
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found" + username));
    }
}
