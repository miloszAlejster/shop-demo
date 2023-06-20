package com.pb.controller;

import com.pb.dto.ProductDto;
import com.pb.model.Product;
import com.pb.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    @ResponseBody
    List<ProductDto> allProducts() {
        return productService.findAllProducts();
    }

    @PostMapping("/add")
    @ResponseBody
    ProductDto newProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/get")
    @ResponseBody
    ProductDto oneProduct(@RequestParam("id") Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    void deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping(value = "/edit", consumes = "application/x-www-form-urlencoded")
    public String updateUser(ProductDto productDto, HttpServletRequest request, Model model) {
        productService.updateProduct(productDto);
        // TODO: temporary solution
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
