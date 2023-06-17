package com.pb.service;

import com.pb.dto.ProductDto;
import com.pb.model.Product;
import com.pb.repository.ProductRepository;
import com.pb.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void ProductService_findAllProducts_ReturnsProductDtoList() {
        List<Product> productList = Mockito.mock(List.class);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> savedProducts = productService.findAllProducts();

        Assertions.assertThat(savedProducts).isNotNull();
    }

    @Test
    public void ProductService_CreateProduct_ReturnProductDto() {
        Product product = Product.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(20.3)
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(20.3)
                .build();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        productService.createProduct(productDto);

        Assertions.assertThat(productDto.getId()).isEqualTo(product.getId());
        Assertions.assertThat(productDto.getName()).isEqualTo(product.getName());
        Assertions.assertThat(productDto.getDescription()).isEqualTo(product.getDescription());
        Assertions.assertThat(productDto.getPrice()).isEqualTo(product.getPrice());

    }

    @Test
    public void ProductService_DeleteProduct() {
        Long productId = 1L;

        assertAll(() -> productService.deleteProduct(productId));
    }

    @Test
    public void ProductService_GetProductById_ReturnsProductDto() {
        Product product = Product.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(20.3)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));

        ProductDto savedProduct = productService.getProductById(1L);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isEqualTo(1L);
        Assertions.assertThat(savedProduct.getName()).isEqualTo("name");
        Assertions.assertThat(savedProduct.getDescription()).isEqualTo("description");
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(20.3);
    }
}
