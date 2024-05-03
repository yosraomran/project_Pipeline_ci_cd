package com.example.app_devops_pipeline.controllers;

import com.example.app_devops_pipeline.models.Product;
import com.example.app_devops_pipeline.repositories.CategoryRepository;
import com.example.app_devops_pipeline.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Create

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    // Read
    @GetMapping("/get")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    // Update
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id); // Ensure the correct ID is set
        productRepository.save(product);
        return "redirect:/products";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

}
