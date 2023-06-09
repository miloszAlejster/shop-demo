package com.pb.service;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;
import com.pb.model.Order;
import com.pb.model.Product;

import java.util.List;
import java.util.Set;

public interface OrderService {
    List<OrderDto> findAllOrders();
    List<OrderDto> finaAllUsersOrders(Long id);
    void createOrder(OrderDto orderDto);
    void deleteOrder(Long id);
    OrderDto getOrderById(Long id);
    void updateOrder(OrderDto orderDto);
    void addProductToOrder(Long orderId, ProductDto productDto);
    void removeProductFromOrder(Long orderId, ProductDto productDto);
}
