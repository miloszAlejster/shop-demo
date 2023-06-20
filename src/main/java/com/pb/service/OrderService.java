package com.pb.service;

import com.pb.dto.OrderDto;
import com.pb.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> findAllOrders();
    List<OrderDto> findAllUsersOrders(Long id);
    OrderDto createOrder(Order order);
    OrderDto createOrderWithUserId(Long userId);
    void deleteOrder(Long id);
    OrderDto getOrderById(Long id);
    Double getOrderSum(Long orderId);
//    void updateOrder(OrderDto orderDto);
    void addProductToOrder(Long orderId, Long productId);
    void removeProductFromOrder(Long orderId, Long productId);
    Optional<OrderDto> findActiveOrder(Long userId);
}
