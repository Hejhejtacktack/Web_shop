package com.example.web_shop.service;

import com.example.web_shop.model.CartItem;
import com.example.web_shop.model.Product;
import com.example.web_shop.model.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class ShoppingCartService {

    private final HttpSession httpSession;
    private final ShoppingCart shoppingCart;

    @Autowired
    public ShoppingCartService(HttpSession httpSession, ShoppingCart shoppingCart) {
        this.httpSession = httpSession;
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getCurrentCart() {
        return this.shoppingCart;
    }

    public List<CartItem> getCartItems() {
        return shoppingCart.getCartItems();
    }

    public double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return totalPrice;
    }

    public void incrementProductQuantity(Long productId, int quantity) {
        // Find the cart item corresponding to the specified product ID
        Optional<CartItem> cartItemOptional = this.shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        // Increment the quantity if the product is found in the cart
        cartItemOptional.ifPresent(cartItem -> cartItem.setQuantity(quantity));
    }

    public void addItemToCart(Product product, int quantity) {
        // Check if the product is already in the cart
        Optional<CartItem> existingItem = this.shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity if the product is already in the cart
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            // Otherwise, add a new cart item
            CartItem newItem = new CartItem(product, quantity);
            this.shoppingCart.getCartItems().add(newItem);
        }
    }

    public void removeItemFromCart(Long productId) {
        this.shoppingCart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clearCart() {
        this.shoppingCart.getCartItems().clear();
    }
}
