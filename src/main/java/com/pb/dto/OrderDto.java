package com.pb.dto;

import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class OrderDto {
    public OrderDto() {
    }
    public OrderDto(Long id, Boolean isActive, UserDto user, List<ProductDto> products) {
        this.id = id;
        this.isActive = isActive;
        this.user = user;
        this.products.addAll(products);
    }
    public OrderDto(Order order) {
        this.id = order.getId();
        this.isActive = order.getIsActive();
        this.user = new UserDto(order.getUser());
        if(order.getProducts().isEmpty()) return;
        for(Product product : order.getProducts()) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            products.add(productDto);
        }
    }
    private Long id;
    private Boolean isActive;
    private UserDto user;
    private List<ProductDto> products = new ArrayList<>();
}
