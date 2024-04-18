package com.example.web_shop.controller;

import com.example.web_shop.model.Product;
import com.example.web_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductsPage(Model model) {
        model.addAttribute("products", this.productService.getProducts());
        model.addAttribute("productAdded", "");
        return "products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        // Add any necessary model attributes for the product form
        model.addAttribute("product", new Product());
        return "addProductForm"; // Thymeleaf template for the product management page
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/products"; // Redirect back to the products page after adding the product
    }

    @GetMapping("/products/search")
    public String showSearchProductForm(Model model) {
        // Add any necessary model attributes for the product form
        model.addAttribute("product", new Product());
        return "searchProductPage"; // Thymeleaf template for the product management page
    }

//    @PostMapping("/products/search")
//    public String addProduct(@ModelAttribute("product") Product product) {
//        productService.addProduct(product);
//        return "redirect:/products"; // Redirect back to the products page after adding the product
//    }
}
