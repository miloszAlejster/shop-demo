package com.pb.dto;

import com.pb.model.Product;
import com.pb.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    private User user;
    private List<Product> products;
}
