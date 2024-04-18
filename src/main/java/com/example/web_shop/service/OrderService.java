package com.example.web_shop.service;

import com.example.web_shop.model.*;
import com.example.web_shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order createOrder(ShoppingCart shoppingCart) {
        // TODO THIS IS WEIRD
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            // Creating an OrderItem and setting the relationship to Order
            OrderItem orderItem = new OrderItem(product, quantity);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(); // TODO CHANGE

        return this.orderRepo.save(order);
    }
}
