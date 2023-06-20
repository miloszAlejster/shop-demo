package com.pb.controller;

import com.pb.dto.ProductDto;
import com.pb.dto.UserDto;
import com.pb.model.Product;
import com.pb.model.Role;
import com.pb.model.User;
import com.pb.service.ProductService;
import com.pb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ProductService productService;

    public AdminController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/users")
    String adminUsers(Model model) {
        model.addAttribute("title", "Shop - Admin");
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("usersList", users);
        return "admin-users";
    }

    @GetMapping("/users/edit")
    String adminUsersEdit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", "Shop - Admin");
        UserDto userDto = userService.getUserById(id);
        model.addAttribute("user", userDto);
        return "admin-users-edit";
    }

    @GetMapping("/users/new")
    String adminUserNew(Model model) {
        model.addAttribute("title", "Shop - Admin");
        model.addAttribute("user", new User());
        return "admin-users-new";
    }
    @PostMapping("/user/create")
    public String processUserCreate(User user, Model model) {
        // user already exists
        boolean isUserPresent = userService.checkEmail(user.getEmail());
        if(isUserPresent) {
            return "redirect:/admin/user/create?error=true";
        }
        user.setRole(Role.USER);
        userService.createUser(user);
        model.addAttribute("message", "User created successfully");
        return "redirect:/admin/users";
    }
    @GetMapping("/products")
    String adminProducts(Model model) {
        model.addAttribute("title", "Shop - Admin");
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("productList", products);
        return "admin-products";
    }
    @GetMapping("/products/edit")
    String adminProductEdit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", "Shop - Admin");
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "admin-products-edit";
    }

    @GetMapping("/products/new")
    String adminProductNew(Model model) {
        model.addAttribute("title", "Shop - Admin");
        model.addAttribute("product", new Product());
        return "admin-products-new";
    }
    @PostMapping("/products/create")
    public String processProductCreate(Product product, Model model) {
        productService.createProduct(product);
        return "redirect:/admin/products";
    }
}
