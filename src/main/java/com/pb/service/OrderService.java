package com.pb.service;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAllOrders();
    List<OrderDto> findAllUsersOrders(Long id);
    void createOrder(OrderDto orderDto);
    void deleteOrder(Long id);
    OrderDto getOrderById(Long id);
    void updateOrder(OrderDto orderDto);
    void addProductToOrder(Long orderId, ProductDto productDto);
    void removeProductFromOrder(Long orderId, ProductDto productDto);
}
