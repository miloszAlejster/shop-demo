package com.pb.service.impl;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import com.pb.repository.OrderRepository;
import com.pb.repository.ProductRepository;
import com.pb.repository.UserRepository;
import com.pb.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        logger.info("All orders found");
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findAllUsersOrders(Long id) {
        List<Order> orders = orderRepository.findAll();
        logger.info("All orders found");
        List<Order> usersOrders = orders.stream().filter((order) -> id.equals(order.getUser().getId())).toList();
        logger.info("All orders of user with id: " + id + " found");
        List<OrderDto> usersOrdersDto = new ArrayList<>();
        for(Order order : usersOrders) {
            usersOrdersDto.add(new OrderDto(order));
        }
        return usersOrdersDto;
    }

    @Override
    public OrderDto createOrder(Order order) {
        Order createdOrder = orderRepository.save(order);
        logger.info("New order created: " + order.toString());
        return new OrderDto(createdOrder);
    }

    @Override
    public OrderDto createOrderWithUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Order order = new Order();
        order.setIsActive(true);
        order.setUser(user.get());
        Order createdOrder = orderRepository.save(order);
        return new OrderDto(createdOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        logger.info("Order with id: " + id + " deleted.");
    }

    @Override
    public OrderDto getOrderById(Long id) {
//       Order order = orderRepository.findById(id).get();
//        logger.info("Order with id: " + id + " found.");
//
//       return new OrderDto(order);

       Optional<Order> optionalOrder = orderRepository.findById(id);
       if(optionalOrder.isPresent()) {
           Order order = optionalOrder.get();
           logger.info("Order with id: " + id + " found.");
           return new OrderDto(order);
       }
       else {
           logger.info("Order with id: " + id + " not found.");
           return null;
       }
    }

    @Override
    public Double getOrderSum(Long orderId) {
//        OrderDto orderDto = getOrderById(orderId);
//        Double sum = 0.0;
//        for(ProductDto p : orderDto.getProducts()) {
//            sum += p.getPrice();
//        }
//        return sum;

        OrderDto orderDto = getOrderById(orderId);
        Double sum = 0.0;
        if (orderDto != null) {
            for(ProductDto p : orderDto.getProducts()) {
                sum += p.getPrice();
            }
        }
        return sum;
    }

    @Override
    public void setOrderInactive(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            logger.info("Order with id: " + orderId + " found.");
            Order order = optionalOrder.get();
            order.setIsActive(false);
            orderRepository.save(order);
            logger.info("Order with id: " + " is now inactive.");
        }
        else {
            logger.error("Order with id: " + orderId + " not found.");
        }
    }

//    @Override
//    public void updateOrder(OrderDto orderDto) {
////        Order order = maptoOrder(orderDto);
////        orderRepository.save(order);
//        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getId());
//        if (optionalOrder.isPresent()) {
//            logger.info("Order with id: " + orderDto.getId() + " found.");
//            Order order = optionalOrder.get();
//            order.setProducts(orderDto.getProducts());
//            order.setUser();
//            orderRepository.save(order);
//            logger.info("Order with id: " + orderDto.getId() + " updated.");
//        }
//        else {
//            logger.error("Order with id: " + orderDto.getId() + " not found.");
//        }
//    }

    @Override
    public void addProductToOrder(Long orderId, Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent() && optionalProduct.isPresent()) {
            logger.info("Order with id: " + orderId + " found.");
            logger.info("Product with id: " + productId + " found.");
            Order order = optionalOrder.get();
            List<Product> products = order.getProducts();
            products.add(optionalProduct.get());
            order.setProducts(products);
            orderRepository.save(order);
            logger.info("Added product: " + optionalProduct.get() + " to order with id: " + orderId);
        }
        else {
            logger.error("Order with id: " + orderId + " not found.");
        }
    }

    @Override
    public void removeProductFromOrder(Long orderId, Long productId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            logger.info("Order with id: " + orderId + " found.");
            Order order = optionalOrder.get();
            List<Product> products = order.getProducts();
            Optional<Product> product = productRepository.findById(productId);
            products.remove(product.get());
            order.setProducts(products);
            orderRepository.save(order);
            logger.info("Removed product: " + product.toString() + " from order with id: " + orderId);
        }
        else {
            logger.error("Order with id: " + orderId + " not found.");
        }
    }

    @Override
    public Optional<OrderDto> findActiveOrder(Long userId) {
        List<OrderDto> allOrders = findAllUsersOrders(userId);
        Optional<OrderDto> activeOrder = allOrders.stream().filter(OrderDto::getIsActive).findFirst();
        return activeOrder;
    }

//    private OrderDto mapToOrderDto(Order order) {
//        OrderDto orderDto = OrderDto.builder()
//                .id(order.getId())
//                .user(order.getUser())
//                .isActive(order.getIsActive())
//                .products(order.getProducts())
//                .build();
//        return orderDto;
//    }
//
//    private Order maptoOrder(OrderDto orderDto) {
//        Order order = Order.builder()
//                .id(orderDto.getId())
//                .user(orderDto.getUser())
//                .isActive(orderDto.getIsActive())
//                .products(orderDto.getProducts())
//                .build();
//        return order;
//    }
//
//    private Product mapToProduct(ProductDto productDto) {
//        Product product = Product.builder()
//                .id(productDto.getId())
//                .name(productDto.getName())
//                .description(productDto.getDescription())
//                .price(productDto.getPrice())
//                .orders(productDto.getOrders())
//                .build();
//        return product;
//    }
}
