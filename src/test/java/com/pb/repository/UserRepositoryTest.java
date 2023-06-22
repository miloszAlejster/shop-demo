package com.pb.repository;

import com.pb.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void UserRepository_SavaAll_ReturnSavedUser() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        User savedUser = repository.save(user);
        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }
    @Test
    public void UserRepository_GetAll_ReturnAllUsers() {
        User user1 = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        User user2 = User.builder()
                .firstname("Greg")
                .lastname("Korzenov")
                .email("GKorzen@gmail.com").
                password("456")
                .build();
        repository.save(user1);
        repository.save(user2);
        List<User> users = repository.findAll();
        Assertions.assertNotNull(users);
        //Assertions.assertEquals(2, users.size());
        Assertions.assertEquals(5, users.size()); // because commandLineRunner creates 3
    }
    @Test
    public void UserRepository_FindById_ReturnUser() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        repository.save(user);
        User foundUser = repository.findById(user.getId()).get();
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
    }
    @Test
    public void UserRepository_DeleteUser_ReturnUserIsEmpty() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        repository.save(user);
        repository.deleteById(user.getId());
        Optional<User> deletedUser = repository.findById(user.getId());
        Assertions.assertTrue(deletedUser.isEmpty());
    }
}
