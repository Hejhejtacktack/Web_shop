package com.example.web_shop.model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Order {
    private Long id;
    private User customer;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Order(User customer, List<OrderItem> orderItems) {
        this.customer = customer;
        this.orderItems = orderItems;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.totalPrice = calculateTotalPrice(); // Calculate total price based on order items
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (OrderItem item : orderItems) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
