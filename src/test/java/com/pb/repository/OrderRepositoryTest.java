package com.pb.repository;

import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @Test
    public void OrderRepository_SaveAll_ReturnSavedOrder() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        Product product = Product.builder()
                .name("Product1")
                .description("Description1")
                .price(20)
                .build();
        Product product2 = Product.builder()
                .name("Product2")
                .description("Description2")
                .price(30)
                .build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        Order order = Order.builder().user(user).products(products).build();
        Order savedOrder = repository.save(order);
        Assertions.assertNotNull(savedOrder);
    }
    @Test
    public void OrderRepository_GetAll_ReturnAllOrders() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        Product product = Product.builder()
                .name("Product1")
                .description("Description1")
                .price(20)
                .build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order1 = Order.builder().user(user).products(products).build();
        Order order2 = Order.builder().user(user).products(products).build();
        repository.save(order1);
        repository.save(order2);
        List<Order> orders = repository.findAll();
        Assertions.assertNotNull(orders);
        Assertions.assertEquals(2, orders.size());
    }
    @Test
    public void OrderRepository_FindById_ReturnOrder() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        Product product = Product.builder()
                .name("Product1")
                .description("Description1")
                .price(20)
                .build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = Order.builder().user(user).products(products).build();
        repository.save(order);
        Order foundOrder = repository.findById(order.getId()).get();
        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(order.getId(), foundOrder.getId());
    }
    @Test
    public void OrderRepository_DeleteOrder_ReturnOrderIsEmpty() {
        User user = User.builder()
                .firstname("John")
                .lastname("Hofovof")
                .email("JohnH@gmail.com").
                password("123")
                .build();
        Product product = Product.builder()
                .name("Product1")
                .description("Description1")
                .price(20)
                .build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = Order.builder().user(user).products(products).build();
        repository.save(order);
        repository.deleteById(order.getId());
        Optional<Order> deletedOrder = repository.findById(order.getId());
        Assertions.assertTrue(deletedOrder.isEmpty());
    }
}
