package com.example.app_devops_pipeline.controllers;

import com.example.app_devops_pipeline.models.Product;
import com.example.app_devops_pipeline.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNewProductForm() {
        Model model = mock(Model.class);

        String viewName = productController.newProductForm(model);

        assertEquals("products/new", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }


    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        String viewName = productController.createProduct(product);

        assertEquals("redirect:/products", viewName);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testEditProductForm() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        String viewName = productController.editProductForm(productId, model);

        assertEquals("products/edit", viewName);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.save(product)).thenReturn(product);

        String viewName = productController.updateProduct(productId, product);

        assertEquals("redirect:/products", viewName);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        String viewName = productController.deleteProduct(productId);

        assertEquals("redirect:/products", viewName);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
