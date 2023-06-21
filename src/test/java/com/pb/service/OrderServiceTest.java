package com.pb.service;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import com.pb.repository.OrderRepository;
import com.pb.repository.ProductRepository;
import com.pb.service.impl.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void OrderService_SetOrderInactive_OrderFound_ShouldSetOrderInactive() {
        Order order = Order.builder()
                .id(1L)
                .isActive(true)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.setOrderInactive(1L);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
        Assertions.assertThat(false).isEqualTo(order.getIsActive());
    }

    @Test
    public void OrderService_SetOrderInactive_OrderNotFound_ShouldNotSetOrderInactive() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        orderService.setOrderInactive(orderId);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void OrderService_findAllOrders_ReturnsOrderDtoList() {
        List<Order> orderList = Mockito.mock(List.class);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderDto> savedOrders = orderService.findAllOrders();

        verify(orderRepository, times(1)).findAll();
        Assertions.assertThat(savedOrders).isNotNull();
    }

    @Test
    public void OrderService_findAllUsersOrders_ReturnsOrderDtoList() {
        Long userId = 1L;
        List<Order> orderList = Mockito.mock(List.class);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderDto> savedOrders = orderService.findAllUsersOrders(userId);

        verify(orderRepository, times(1)).findAll();
        Assertions.assertThat(savedOrders).isNotNull();
    }

    @Test
    public void OrderService_CreateOrder() {
        Order order = Order.builder()
                .id(1L)
                .user(new User())
                .products(new ArrayList<Product>())
                .build();

        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        OrderDto orderDto = orderService.createOrder(order);

        verify(orderRepository, times(1)).save(order);
        Assertions.assertThat(orderDto.getId()).isEqualTo(new OrderDto(order).getId());
        Assertions.assertThat(orderDto.getUser()).isEqualTo(new OrderDto(order).getUser());
        Assertions.assertThat(orderDto.getProducts()).isEqualTo(new OrderDto(order).getProducts());
    }

    @Test
    public void OrderService_DeleteOrder() {
        Long orderId = 1L;

        orderService.deleteOrder(orderId);

        //assertAll(() -> orderService.deleteOrder(orderId));
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void OrderService_GetOrderById_OrderFound_ReturnsOrderDto() {
        Order order = Order.builder()
                .id(1L)
                .user(new User())
                .products(new ArrayList<Product>())
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));

        OrderDto savedOrder = orderService.getOrderById(1L);

        verify(orderRepository, times(1)).findById(1L);
        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getId()).isEqualTo(1L);
        Assertions.assertThat(savedOrder.getUser()).isEqualTo(new UserDto());
        Assertions.assertThat(savedOrder.getProducts()).isEqualTo(new ArrayList<ProductDto>());
    }

    @Test
    public void OrderService_GetOrderById_OrderNotFound_ReturnsNull() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderDto savedOrder = orderService.getOrderById(orderId);

        verify(orderRepository, times(1)).findById(1L);
        Assertions.assertThat(savedOrder).isNull();
    }

//    @Test
//    public void OrderService_UpdateOrder_OrderExists_OrderUpdated() {
//        OrderDto orderDto = OrderDto.builder()
//                .id(1L)
//                .user(User.builder()
//                        .id(1L)
//                        .email("email@example.com")
//                        .build())
//                .products(new ArrayList<Product>())
//                .build();
//        Order order = Order.builder()
//                .id(1L)
//                .user(new User())
//                .products(new ArrayList<Product>())
//                .build();
//
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
//
//        orderService.updateOrder(orderDto);
//
//        verify(orderRepository).save(order);
//
//        Assertions.assertThat(orderDto.getProducts()).isEqualTo(order.getProducts());
//        Assertions.assertThat("email@example.com").isEqualTo(order.getUser().getEmail());
//        Assertions.assertThat(orderDto.getUser()).isEqualTo(order.getUser());
//    }

//    @Test
//    public void OrderService_UpdateOrder_OrderDontExists_NothingUpdated() {
//        OrderDto orderDto = OrderDto.builder()
//                .id(1L)
//                .user(User.builder()
//                        .id(1L)
//                        .email("email@example.com")
//                        .build())
//                .products(new ArrayList<Product>())
//                .build();
//
//        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
//
//        orderService.updateOrder(orderDto);
//
//        verify(orderRepository, never()).save(any(Order.class));
//    }

    @Test
    public void OrderService_ShouldAddProductToOrder_OrderExists() {
        Product product = Product.builder()
                .id(1L)
                .build();
        List<Product> existingProducts = new ArrayList<>();
        Order existingOrder = Order.builder()
                .id(1L)
                .user(new User())
                .products(existingProducts)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        orderService.addProductToOrder(existingOrder.getId(), product.getId());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(existingOrder);
        Assertions.assertThat(1).isEqualTo(existingOrder.getProducts().size());
    }

    @Test
    public void OrderService_ShouldNotAddProductToOrder_OrderDontExists() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .orders(new ArrayList<>())
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        orderService.addProductToOrder(1L, productDto.getId());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void OrderService_ShouldRemoveProductToOrder_OrderExists() {
        Product product = Product.builder()
                .id(1L)
                .build();
        List<Product> existingProducts = new ArrayList<>();
        existingProducts.add(Product.builder()
                        .id(1L)
                .build());
        Order existingOrder = Order.builder()
                .id(1L)
                .user(new User())
                .products(existingProducts)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        orderService.removeProductFromOrder(1L, 1L);

        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(existingOrder);
        Assertions.assertThat(0).isEqualTo(existingOrder.getProducts().size());
    }

    @Test
    public void OrderService_ShouldNotRemoveProductToOrder_OrderDontExists() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .orders(new ArrayList<>())
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        orderService.removeProductFromOrder(1L, productDto.getId());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void OrderService_GetOrderSum_OrderExists_ReturnsSum() {
        Product product1 = Product.builder()
                .id(1L)
                .price(10.0)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .price(20.0)
                .build();
        ArrayList<Product> products = new ArrayList<>();
        products.add(product2);
        products.add(product1);
        Order order = Order.builder()
                .id(1L)
                .user(new User())
                .products(products)
                .build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Double expectedSum = 30.0;
        Double actualSum = orderService.getOrderSum(order.getId());

        Assertions.assertThat(actualSum).isEqualTo(expectedSum);

        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    public void OrderService_GetOrderSum_OrderNotExists_ReturnsZero() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Double expectedSum = 0.0;
        Double actualSum = orderService.getOrderSum(orderId);

        Assertions.assertThat(actualSum).isEqualTo(expectedSum);
        verify(orderRepository, times(1)).findById(orderId);
    }
}
