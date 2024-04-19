package com.example.web_shop.controller;

import com.example.web_shop.model.Product;
import com.example.web_shop.service.ProductService;
import com.example.web_shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ProductController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/products")
    public String getProductsPage(Model model) {
        model.addAttribute("products", this.productService.getProducts());
        model.addAttribute("productAdded", "");
        return "products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {

        model.addAttribute("categories", this.productService.getCategories());
        model.addAttribute("product", new Product());
        return "addProductForm";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        this.productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    public String showSearchProductForm(Model model) {

        model.addAttribute("search", "");
        return "searchProductPage";
    }

    @PostMapping("/products/search")
    public String searchProducts(@RequestParam String search, Model model) {
        List<Product> products = this.productService.searchProducts(search);
        model.addAttribute("products", products);
        return "searchProductPage";
    }

    @PostMapping("/products/search/addToCart")
    public String addProduct(@RequestParam("productId") Long productId,
                             RedirectAttributes redirectAttributes) {
        Product product = this.productService.getProduct(productId);
        this.shoppingCartService.addItemToCart(product, 1);

        redirectAttributes.addAttribute("productAdded", true);

        return "redirect:/products/search";
    }

    @GetMapping("/products/filtered")
    public String filterProductsByCategory(@RequestParam(required = false) String category, Model model) {
        List<Product> filteredProducts;
        if (category != null && !category.isEmpty()) {
            filteredProducts = this.productService.getProductsByCategory(category);
        } else {
            filteredProducts = this.productService.getProducts();
        }
        model.addAttribute("products", filteredProducts);
        model.addAttribute("categories", this.productService.getCategories());
        return "filterProductsPage"; // Thymeleaf template for filtered products
    }
}
