package com.pb.controller;

import com.pb.dto.OrderDto;
import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Order;
import com.pb.model.Product;
import com.pb.model.User;
import com.pb.service.OrderService;
import com.pb.service.ProductService;
import com.pb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class IndexController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public IndexController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }
    Logger logger = LoggerFactory.getLogger(IndexController.class);


    @GetMapping("/")
    String home(Model model) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("title", "Shop");
        model.addAttribute("principal", principal);
        logger.info("[GET] Home Page");
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Shop - Login");
        logger.info("[GET] Login Page");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.info("User logout");
        return "redirect:/login";
    }

    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("title", "Shop - Register");
        model.addAttribute("user", new User());
        logger.info("[GET] Register Page");
        return "register";
    }


    @GetMapping("/admin")
    String adminDashboard(Model model) {
        model.addAttribute("title", "Shop - Admin");
        return "admin-dashboard";
    }
    @GetMapping("/my-orders")
    public String my_orders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("title", "Shop - My orders");
        Optional<UserDto> activeOptionalUser = userService.findByEmail(userDetails.getUsername());
        UserDto userDto = new UserDto();
        if(activeOptionalUser.isPresent()) {
            userDto = activeOptionalUser.get();
        }
        List<OrderDto> orders = orderService.findAllUsersOrders(userDto.getId());
        model.addAttribute("orders", orders);
        List<Double> sums = new ArrayList<>();
        for (OrderDto orderDto : orders) {
            Double totalPrice = orderService.getOrderSum(orderDto.getId());
            sums.add(totalPrice);
        }
        model.addAttribute("sums", sums);
        return "my_orders";
    }

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("title", "Shop - Cart");
        Optional<UserDto> activeUser = userService.findByEmail(userDetails.getUsername());
        Optional<OrderDto> activeOptionalOrder = orderService.findActiveOrder(activeUser.get().getId());
        OrderDto orderDto = new OrderDto();
        if(activeOptionalOrder.isPresent()) {
            orderDto = activeOptionalOrder.get();
        }
        if(activeOptionalOrder.isEmpty()) {
            orderDto = orderService.createOrderWithUserId(activeUser.get().getId());
        }
        List<ProductDto> products = orderDto.getProducts();
        model.addAttribute("products", products);
        Double totalPrice = orderService.getOrderSum(orderDto.getId());
        model.addAttribute("sum", totalPrice);
        model.addAttribute("orderId", orderDto.getId());
        return "cart";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("title", "Shop - Products");
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "products";

    }
}
