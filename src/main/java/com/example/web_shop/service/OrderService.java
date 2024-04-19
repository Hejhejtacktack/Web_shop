package com.example.web_shop.service;

import com.example.web_shop.model.*;
import com.example.web_shop.repository.OrderRepository;
import com.example.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    public Order createOrder(ShoppingCart shoppingCart) {
        // TODO THIS IS WEIRD
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem(product, quantity);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = this.userRepo.findByUsername(userDetails.getUsername()).get();
        order.setUser(user); // TODO CHANGE
        order.setOrderItems(orderItems);
        order.setTotalPrice(); // TODO CHANGE

        return this.orderRepo.save(order);
    }
}
