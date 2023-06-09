package com.pb.service.impl;

import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import com.pb.repository.ProductRepository;
import com.pb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAllUsers() {
        List<Product> products = productRepository.findAll();
        return products.stream().map((product) -> mapToProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).get();
        return mapToProductDto(product);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    public void updateProductName(Long id, String newName) {
        Product product = productRepository.findById(id).get();
        product.setName(newName);
        productRepository.save(product);
    }

    @Override
    public void updateProductDescription(Long id, String newDescription) {
        Product product = productRepository.findById(id).get();
        product.setDescription(newDescription);
        productRepository.save(product);
    }

    @Override
    public void updateUserPrice(Long id, Double newPrice) {
        Product product = productRepository.findById(id).get();
        product.setPrice(newPrice);
        productRepository.save(product);
    }

    @Override
    public void updateUserOrders(Long id, Set<Order> newOrders) {
        Product product = productRepository.findById(id).get();
        product.setOrders(newOrders);
        productRepository.save(product);
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
