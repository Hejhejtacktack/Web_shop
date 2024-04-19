package com.example.web_shop.service;

import com.example.web_shop.model.CartItem;
import com.example.web_shop.model.Product;
import com.example.web_shop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService;
    @BeforeEach
    void setUp() {
        ShoppingCart shoppingCart = new ShoppingCart();
        this.shoppingCartService = new ShoppingCartService(shoppingCart);
    }

    @Test
    void getCurrentCart() {
    }

    @Test
    void getCartItems() {
    }

    @Test
    void calculateTotalPriceWhenNotEmptyTest() {
        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = new Product();
        product1.setPrice(10.0);
        Product product2 = new Product();
        product2.setPrice(15.0);
        cartItems.add(new CartItem(product1, 2));
        cartItems.add(new CartItem(product2, 3));

        double totalPrice = this.shoppingCartService.calculateTotalPrice(cartItems);

        assertEquals(2 * 10.0 + 3 * 15.0, totalPrice);
    }
    @Test
    void calculateTotalPriceWhenEmptyTest() {
        List<CartItem> cartItems = new ArrayList<>();

        double totalPrice = this.shoppingCartService.calculateTotalPrice(cartItems);

        assertEquals(0.0, totalPrice);
    }

    @Test
    void incrementProductQuantity() {
        List<CartItem> cartItems = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        CartItem cartItem = new CartItem(product, 2);
        cartItems.add(cartItem);
        this.shoppingCartService.setCartItems(cartItems);

        this.shoppingCartService.incrementProductQuantity(1L, 5);

        Optional<CartItem> updatedCartItemOptional = shoppingCartService.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(1L))
                .findFirst();

        assertEquals(5, updatedCartItemOptional.get().getQuantity());
    }

    @Test
    void addItemToCart() {
    }

    @Test
    void removeItemFromCart() {
    }
}