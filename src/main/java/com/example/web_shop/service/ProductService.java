package com.example.web_shop.service;

import com.example.web_shop.model.Product;
import com.example.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
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
    }
}
