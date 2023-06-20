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
            User user1 = new User();
            user1.setEmail("user@gmail.com");
            user1.setPassword("123");
            user1.setFirstname("user");
            user1.setLastname("auuuuuu");
            user1.setRole(Role.USER);
            userRepository.save(user1);
            User user2 = new User();
            user2.setEmail("user2@gmail.com");
            user2.setPassword("1232");
            user2.setFirstname("user2");
            user2.setLastname("auuuuuu2");
            user2.setRole(Role.USER);
            userRepository.save(user2);
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin");
            admin.setFirstname("admin");
            admin.setLastname("admin");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        };
    }
}
