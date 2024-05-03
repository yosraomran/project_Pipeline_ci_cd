package com.example.app_devops_pipeline.controllers;

import com.example.app_devops_pipeline.models.Category;
import com.example.app_devops_pipeline.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    // Read
    @GetMapping("/")
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    // Create
    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/new";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    // Update
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id); // Ensure the correct ID is set
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories/";
    }
}

