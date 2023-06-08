package com.pb.repository;

import com.pb.model.Product;
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
public class ProductRepositoryTest {
    @Autowired
    ProductRepository repository;

    @Test
    public void ProductRepository_SavaAll_ReturnSavedProduct() {
        Product product = Product.builder()
                .name("Product")
                .description("Description")
                .price(20)
                .build();
        Product savedProduct = repository.save(product);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getId() > 0);
    }
    @Test
    public void ProductRepository_GetAll_ReturnAllProducts() {
        Product product1 = Product.builder()
                .name("Product")
                .description("Description")
                .price(20)
                .build();
        Product product2 = Product.builder()
                .name("Product")
                .description("Description")
                .price(20)
                .build();
        repository.save(product1);
        repository.save(product2);
        List<Product> products = repository.findAll();
        Assertions.assertNotNull(products);
        Assertions.assertEquals(2, products.size());
    }
    @Test
    public void ProductRepository_FindById_ReturnProduct() {
        Product product = Product.builder()
                .name("Product")
                .description("Description")
                .price(20)
                .build();
        repository.save(product);
        Product foundProduct = repository.findById(product.getId()).get();
        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getId(), foundProduct.getId());
    }
    @Test
    public void ProductRepository_DeleteProduct_ReturnProductIsEmpty() {
        Product product = Product.builder()
                .name("Product")
                .description("Description")
                .price(20)
                .build();
        repository.save(product);
        repository.deleteById(product.getId());
        Optional<Product> deletedProduct = repository.findById(product.getId());
        Assertions.assertTrue(deletedProduct.isEmpty());
    }
}
