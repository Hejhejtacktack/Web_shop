package com.example.web_shop.service;

import com.example.web_shop.model.Category;
import com.example.web_shop.model.Product;
import com.example.web_shop.repository.CategoryRepository;
import com.example.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<Product> getProducts() {
        return this.productRepo.findAll();
    }

    public Product getProduct(String name) throws NullPointerException {
        return this.productRepo.findByName(name)
                .orElseThrow(() -> new NullPointerException("Product with name " + name + " not found"));
    }

    public Product getProduct(Long id) throws NullPointerException {
        return this.productRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("Product with id " + id + " not found"));
    }

    public void addProduct(Product product) {
        this.productRepo.save(product);
    }

    public List<Product> getProductsByCategory(String name) {
        return this.productRepo.findAllByCategoryName(name);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.findByNameContaining(keyword);
    }

    public List<Category> getCategories() {
        return this.categoryRepo.findAll();
    }
}
