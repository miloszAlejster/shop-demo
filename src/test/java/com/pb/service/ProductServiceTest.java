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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void ProductService_UpdateProduct_ProductExists_ProductUpdated() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(20.3)
                .build();

        Product existngProduct = Product.builder()
                .id(1L)
                .name("newname")
                .description("newdescription")
                .price(21.3)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existngProduct));

        productService.updateProduct(productDto);

        verify(productRepository).save(existngProduct);

        Assertions.assertThat(productDto.getName()).isEqualTo(existngProduct.getName());
        Assertions.assertThat(productDto.getDescription()).isEqualTo(existngProduct.getDescription());
        Assertions.assertThat(productDto.getPrice()).isEqualTo(existngProduct.getPrice());
    }

    @Test
    public void ProductService_UpdateProduct_ProductDontExists_NothingUpdated() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(20.3)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.updateProduct(productDto);

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void ProductService_UpdateProductName_ProductExists_ProductNameUpdated() {
        Long productId = 1L;
        String newName = "newname";

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("oldname");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(newName);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        productService.updateProductName(productId, newName);

        verify(productRepository).findById(productId);
        verify(productRepository).save(updatedProduct);
        Assertions.assertThat(newName).isEqualTo(existingProduct.getName());
    }

    @Test
    public void ProductService_UpdateProductName_ProductDontExists_NothingUpdated() {
        Long productId = 1L;
        String newName = "newname";

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        productService.updateProductName(productId, newName);

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void ProductService_UpdateProductDescription_ProductExists_ProductDescriptionUpdated() {
        Long productId = 1L;
        String newDescription = "newdescription";

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setDescription("olddescription");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setDescription(newDescription);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        productService.updateProductDescription(productId, newDescription);

        verify(productRepository).findById(productId);
        verify(productRepository).save(updatedProduct);
        Assertions.assertThat(newDescription).isEqualTo(existingProduct.getDescription());
    }

    @Test
    public void ProductService_UpdateProductDescription_ProductDontExists_NothingUpdated() {
        Long productId = 1L;
        String newDescription = "newdescription";

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        productService.updateProductDescription(productId, newDescription);

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void ProductService_UpdateProductPrice_ProductExists_ProductPriceUpdated() {
        Long productId = 1L;
        double newPrice = 13.12;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setPrice(1.1);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setPrice(newPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        productService.updateProductPrice(productId, newPrice);

        verify(productRepository).findById(productId);
        verify(productRepository).save(updatedProduct);
        Assertions.assertThat(newPrice).isEqualTo(existingProduct.getPrice());
    }

    @Test
    public void ProductService_UpdateProductPrice_ProductDontExists_NothingUpdated() {
        Long productId = 1L;
        double newPrice = 13.12;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        productService.updateProductPrice(productId, newPrice);

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void ProductService_UpdateProductOrders_ProductExists_ProductOrdersUpdated() {
        // TODO
    }

    @Test
    public void ProductService_UpdateProductOrders_ProductDontExists_NothingUpdated() {
        //TODO
    }
}
