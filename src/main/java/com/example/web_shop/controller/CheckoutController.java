package com.example.web_shop.controller;

import com.example.web_shop.model.Order;
import com.example.web_shop.model.ShoppingCart;
import com.example.web_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

@Controller
public class CheckoutController {

    private final OrderService orderService;

    @Autowired
    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute ShoppingCart shoppingCart,
                           Model model) {
        Order order = orderService.createOrder(shoppingCart);
        // Display confirmation page with order details
        model.addAttribute("order", order);
        return "confirmationPage";
    }
}
