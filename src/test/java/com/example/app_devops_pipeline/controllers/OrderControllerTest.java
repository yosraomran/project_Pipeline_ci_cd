package com.example.app_devops_pipeline.controllers;


import com.example.app_devops_pipeline.models.Order;
import com.example.app_devops_pipeline.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Model model;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);

        Model model = mock(Model.class);
        String viewName = orderController.listOrders(model);

        assertEquals("orders/index", viewName);
        verify(model, times(1)).addAttribute("orders", orders);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testNewOrderForm() {
        Model model = mock(Model.class);

        String viewName = orderController.newOrderForm(model);

        assertEquals("orders/new", viewName);
        verify(model, times(1)).addAttribute(eq("order"), any(Order.class));
    }


    @Test
    void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        String viewName = orderController.createOrder(order);

        assertEquals("redirect:/orders/", viewName);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testEditOrderForm() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Model model = mock(Model.class);
        String viewName = orderController.editOrderForm(orderId, model);

        assertEquals("orders/edit", viewName);
        verify(model, times(1)).addAttribute("order", order);
    }

    @Test
    void testUpdateOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.save(order)).thenReturn(order);

        String viewName = orderController.updateOrder(orderId, order);

        assertEquals("redirect:/orders/", viewName);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        String viewName = orderController.deleteOrder(orderId);

        assertEquals("redirect:/orders/", viewName);
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
