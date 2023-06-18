package com.pb;

import com.pb.model.Role;
import com.pb.model.User;
import com.pb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword("123");
            user.setFirstname("user");
            user.setLastname("auuuuuu");
            user.setRole(Role.USER);
            userRepository.save(user);
        };
    }
}
