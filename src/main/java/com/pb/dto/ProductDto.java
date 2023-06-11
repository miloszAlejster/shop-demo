package com.pb.dto;

import com.pb.model.Order;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Set<Order> orders;
}
