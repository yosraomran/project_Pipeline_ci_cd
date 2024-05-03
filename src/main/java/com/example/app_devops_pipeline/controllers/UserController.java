package com.example.app_devops_pipeline.controllers;



import com.example.app_devops_pipeline.models.User;
import com.example.app_devops_pipeline.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Read
    @GetMapping("/")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    // Create
    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users/";
    }

    // Update
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id); // Ensure the correct ID is set
        userRepository.save(user);
        return "redirect:/users/";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users/";
    }
}

