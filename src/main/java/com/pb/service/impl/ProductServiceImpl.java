package com.pb.service.impl;

import com.pb.dto.ProductDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.repository.ProductRepository;
import com.pb.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        logger.info("All products found");
        return products.stream().map((product) -> mapToProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        productRepository.save(product);
        logger.info("New product created: " + product.toString());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        logger.info("Product with id: " + id + " deleted.");
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).get();
        logger.info("Product with id: " + id + " found.");
        return mapToProductDto(product);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
//        Product product = mapToProduct(productDto);
//        productRepository.save(product);

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
        if (optionalProduct.isPresent()) {
            logger.info("Product with id: " + productDto.getId() + " found.");
            Product product = optionalProduct.get();
            product.setDescription(productDto.getDescription());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
            logger.info("Product with id: " + productDto.getId() + " updated.");
        }
        else {
            logger.error("Product with id: " + productDto.getId() + " not found.");
        }
    }

    @Override
    public void updateProductName(Long id, String newName) {
//        Product product = productRepository.findById(id).get();
//        product.setName(newName);
//        productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            logger.info("Product with id: " + id + " found.");
            Product product = optionalProduct.get();
            product.setName(newName);
            productRepository.save(product);
            logger.info("Name of product with id: " + id + "updated. New name: " + newName);
        }
        else {
            logger.error("Product with id: " + id + " not found.");
        }
    }

    @Override
    public void updateProductDescription(Long id, String newDescription) {
//        Product product = productRepository.findById(id).get();
//        product.setDescription(newDescription);
//        productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            logger.info("Product with id: " + id + " found.");
            Product product = optionalProduct.get();
            product.setDescription(newDescription);
            productRepository.save(product);
            logger.info("Description of product with id: " + id + "updated. New description: " + newDescription);
        }
        else {
            logger.error("Product with id: " + id + " not found.");
        }
    }

    @Override
    public void updateProductPrice(Long id, Double newPrice) {
//        Product product = productRepository.findById(id).get();
//        product.setPrice(newPrice);
//        productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            logger.info("Product with id: " + id + " found.");
            Product product = optionalProduct.get();
            product.setPrice(newPrice);
            productRepository.save(product);
            logger.info("Price of product with id: " + id + "updated. New price: " + newPrice);
        }
        else {
            logger.error("Product with id: " + id + " not found.");
        }
    }

    @Override
    public void updateProductOrders(Long id, Set<Order> newOrders) {
//        Product product = productRepository.findById(id).get();
//        product.setOrders(newOrders);
//        productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            logger.info("Product with id: " + id + " found.");
            Product product = optionalProduct.get();
            product.setOrders(newOrders);
            productRepository.save(product);
            logger.info("Orders of product with id: " + id + "updated. New orders: " + newOrders.toString());
        }
        else {
            logger.error("Product with id: " + id + " not found.");
        }
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .orders(product.getOrders())
                .build();
        return productDto;
    }

    private Product mapToProduct(ProductDto productDto) {
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .orders(productDto.getOrders())
                .build();
        return product;
    }
}
