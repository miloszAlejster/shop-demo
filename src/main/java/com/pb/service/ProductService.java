package com.pb.service;

import com.pb.dto.ProductDto;
import com.pb.model.Order;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<ProductDto> findAllProducts();
    void createProduct(ProductDto productDto);
    void deleteProduct(Long id);
    ProductDto getProductById(Long id);
    void updateProduct(ProductDto productDto);
    void updateProductName(Long id, String newName);
    void updateProductDescription(Long id, String newDescription);
    void updateProductPrice(Long id, Double newPrice);
    void updateProductOrders(Long id, Set<Order> newOrders);
}
