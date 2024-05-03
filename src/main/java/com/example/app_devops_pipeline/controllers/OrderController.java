package com.example.app_devops_pipeline.controllers;


import com.example.app_devops_pipeline.models.Order;
import com.example.app_devops_pipeline.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    // Read
    @GetMapping("/")
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/index";
    }

    // Create
    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/new";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderRepository.save(order);
        return "redirect:/orders/";
    }

    // Update
    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "orders/edit";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        order.setId(id); // Ensure the correct ID is set
        orderRepository.save(order);
        return "redirect:/orders/";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders/";
    }
}

