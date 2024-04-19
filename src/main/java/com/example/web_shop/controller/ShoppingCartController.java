package com.example.web_shop.controller;

import com.example.web_shop.model.CartItem;
import com.example.web_shop.model.Product;
import com.example.web_shop.service.ProductService;
import com.example.web_shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String viewCartPage(Model model) {
        List<CartItem> cartItems = this.shoppingCartService.getCartItems();
        model.addAttribute("cartItems", cartItems);

        double totalPrice = this.shoppingCartService.calculateTotalPrice(cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "shoppingCart";
    }

    @PostMapping("/addToCart")
    public String addProduct(@RequestParam("productId") Long productId,
                              RedirectAttributes redirectAttributes) {
        Product product = this.productService.getProduct(productId);
        this.shoppingCartService.addItemToCart(product, 1);

        redirectAttributes.addAttribute("productAdded", true);

        return "redirect:/products";
    }

    @PostMapping("/cart/increment")
    public String incrementProductQuantity(@RequestParam("productId") Long productId,
                                           @RequestParam("amount") int amount) {
        if (amount <= 0) {
            this.shoppingCartService.removeItemFromCart(productId);
        } else {
            this.shoppingCartService.incrementProductQuantity(productId, amount);
        }

        return "redirect:/cart";
    }
}
