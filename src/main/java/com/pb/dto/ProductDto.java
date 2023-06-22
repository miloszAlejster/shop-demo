package com.pb.dto;

import com.pb.model.Order;
import com.pb.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProductDto {
    public ProductDto() {

    }
    public ProductDto(Long id, String name, String description, double price, List<OrderDto> orders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.orders.addAll(orders);
    }
    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        if(product.getOrders().isEmpty()) return;;
        for(Order order : product.getOrders()) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setIsActive(order.getIsActive());
            orders.add(orderDto);
        }
    }
    private Long id;
    private String name;
    private String description;
    private double price;
    private List<OrderDto> orders = new ArrayList<>();
}
