package com.pb.service.impl;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import com.pb.repository.OrderRepository;
import com.pb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((order) -> mapToOrderDto(order)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> finaAllUsersOrders(Long id) {
        List<Order> orders = orderRepository.findAll();
        List<Order> usersOrders = orders.stream().filter((order) -> id.equals(order.getUser().getId())).toList();
        return usersOrders.stream().map(order -> mapToOrderDto(order)).collect(Collectors.toList());
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        Order order = maptoOrder(orderDto);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto getOrderById(Long id) {
       Order order = orderRepository.findById(id).get();
       return mapToOrderDto(order);
    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        Order order = maptoOrder(orderDto);
        orderRepository.save(order);
    }

    @Override
    public void addProductToOrder(Long orderId, ProductDto productDto) {
        Order order = orderRepository.findById(orderId).get();
        List<Product> products = order.getProducts();
        products.add(mapToProduct(productDto));
        order.setProducts(products);
        orderRepository.save(order);
    }

    @Override
    public void removeProductFromOrder(Long orderId, ProductDto productDto) {
        Order order = orderRepository.findById(orderId).get();
        List<Product> products = order.getProducts();
        products.remove(mapToProduct(productDto));
        order.setProducts(products);
        orderRepository.save(order);
    }

    private OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .user(order.getUser())
                .products(order.getProducts())
                .build();
        return orderDto;
    }

    private Order maptoOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .user(orderDto.getUser())
                .products(orderDto.getProducts())
                .build();
        return order;
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
