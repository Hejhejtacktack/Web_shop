package com.example.web_shop.controller;

import com.example.web_shop.model.Order;
import com.example.web_shop.model.OrderStatus;
import com.example.web_shop.model.ShoppingCart;
import com.example.web_shop.service.OrderService;
import com.example.web_shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public CheckoutController(OrderService orderService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        ShoppingCart shoppingCart = this.shoppingCartService.getCurrentCart();
        Order order = orderService.createOrder(shoppingCart);

        model.addAttribute("order", order);
        model.addAttribute("username", principal.getName());
        return "confirmationPage";
    }

    @GetMapping("/checkout/review")
    public String reviewOrders(Model model, Principal principal) {
        model.addAttribute("orders", this.orderService.getAllOrders());
        model.addAttribute("statuses", this.orderService.getAllStatuses());
        model.addAttribute("username", principal.getName());
        return "reviewOrders";
    }

    @PostMapping("/checkout/updateStatus")
    public String updateStatus(@RequestParam("orderId") Long orderId,
                               @RequestParam("status") OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/checkout/review";
    }
}
