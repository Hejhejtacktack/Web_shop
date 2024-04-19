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
    private final ShoppingCart shoppingCart;

    @Autowired
    public ShoppingCartService(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public ShoppingCart getCurrentCart() {
        return this.shoppingCart;
    }

    public List<CartItem> getCartItems() {
        return shoppingCart.getCartItems();
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.shoppingCart.setCartItems(cartItems);
    }

    public double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return totalPrice;
    }

    public void incrementProductQuantity(Long productId, int quantity) {
        Optional<CartItem> cartItemOptional = this.shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        cartItemOptional.ifPresent(cartItem -> cartItem.setQuantity(quantity));
    }

    public void addItemToCart(Product product, int quantity) {
        Optional<CartItem> existingItem = this.shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) { // If item is in cart already
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else { // if no item is in cart
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
