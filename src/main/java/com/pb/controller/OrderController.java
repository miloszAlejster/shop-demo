package com.pb.controller;

import com.pb.dto.OrderDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.User;
import com.pb.service.OrderService;
import com.pb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseBody
    OrderDto createOrder(@RequestBody Order order) {
        order.setIsActive(true);
        return orderService.createOrder(order);
    }

    @PostMapping("/addProductToOrder")
    public String addProductToActiveOrder(
            @RequestParam("productId") Long productId,
            HttpServletRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        String referer = request.getHeader("Referer");
        Optional<UserDto> activeUser = userService.findByEmail(userDetails.getUsername());
        if (activeUser.isEmpty()){
            return "redirect:"+ referer;
        }
        Optional<OrderDto> activeOrder = orderService.findActiveOrder(activeUser.get().getId());
        if (activeOrder.isEmpty()) {
            OrderDto createdOrder = orderService.createOrderWithUserId(activeUser.get().getId());
            orderService.addProductToOrder(createdOrder.getId(), productId);
        } else {
            orderService.addProductToOrder(activeOrder.get().getId(), productId);
        }
        return "redirect:"+ referer;
    }
    @DeleteMapping("/removeProductFromOrder")
    @ResponseStatus(value = HttpStatus.OK)
    void removeProductFromOrder(
            @RequestParam("productId") Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserDto> activeUser = userService.findByEmail(userDetails.getUsername());
        Optional<OrderDto> activeOrder = orderService.findActiveOrder(activeUser.get().getId());
        orderService.removeProductFromOrder(activeOrder.get().getId(), productId);
    }
}
