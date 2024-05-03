package com.example.app_devops_pipeline.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.app_devops_pipeline.models.Category;
import com.example.app_devops_pipeline.repositories.CategoryRepository;
import com.example.app_devops_pipeline.controllers.CategoryController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;


import static org.mockito.Mockito.mock;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CategoryControllerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Model model;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListCategories() {
        List<Category> categories = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categories);

        String viewName = categoryController.listCategories(model);

        assertEquals("categories/index", viewName);
        verify(model, times(1)).addAttribute("categories", categories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testNewCategoryForm() {
        Model model = mock(Model.class);

        String viewName = categoryController.newCategoryForm(model);

        assertEquals("categories/new", viewName);
        verify(model).addAttribute(eq("category"), any(Category.class));
    }


    @Test
    void testCreateCategory() {
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        String viewName = categoryController.createCategory(category);

        assertEquals("redirect:/categories/", viewName);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testEditCategoryForm() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        String viewName = categoryController.editCategoryForm(categoryId, model);

        assertEquals("categories/edit", viewName);
        verify(model, times(1)).addAttribute("category", category);
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.save(category)).thenReturn(category);

        String viewName = categoryController.updateCategory(categoryId, category);

        assertEquals("redirect:/categories/", viewName);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        String viewName = categoryController.deleteCategory(categoryId);

        assertEquals("redirect:/categories/", viewName);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
