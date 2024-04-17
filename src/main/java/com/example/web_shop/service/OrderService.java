package com.example.web_shop.service;

import com.example.web_shop.model.*;
import com.example.web_shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order createOrder(ShoppingCart shoppingCart) {
        // Create a new list to store order items
        List<OrderItem> orderItems = new ArrayList<>();

        // Iterate over cart items and convert them to order items
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            // Create an order item and add it to the list
            OrderItem orderItem = new OrderItem(product, quantity);
            orderItems.add(orderItem);
        }

        // Create the order using the user and order items
        Order order = new Order(new User(), orderItems);

        // Save the order to the database
        return this.orderRepo.save(order);
    }
}
