package com.pb.controller;

import com.pb.dto.UserDto;
import com.pb.model.User;
import com.pb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {
    @GetMapping("/")
    String home(Model model) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("title", "Shop");
        model.addAttribute("principal", principal);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Shop - Login");
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("title", "Shop - Register");
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/my-orders")
    public String my_orders(Model model) {
        model.addAttribute("title", "Shop - My orders");
        return "my_orders";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("title", "Shop - Cart");
        return "cart";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("title", "Shop - Products");
        return "products";
    }
}
