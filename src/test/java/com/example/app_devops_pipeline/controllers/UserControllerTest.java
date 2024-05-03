package com.example.app_devops_pipeline.controllers;


import com.example.app_devops_pipeline.models.User;
import com.example.app_devops_pipeline.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testListUsers() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/index"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    void testNewUserForm() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/new"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/create")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/"));
    }

    @Test
    void testEditUserForm() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    void testUpdateUser() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/update/" + id)
                        .param("id", id.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(get("/users/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/"));

        verify(userRepository, times(1)).deleteById(id);
    }
}

