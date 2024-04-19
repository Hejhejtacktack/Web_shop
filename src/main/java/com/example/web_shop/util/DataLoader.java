package com.example.web_shop.util;

import com.example.web_shop.model.Category;
import com.example.web_shop.model.Product;
import com.example.web_shop.repository.CategoryRepository;
import com.example.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Autowired
    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category category1 = new Category("JEANS");
        Category category2 = new Category("SWEATER");
        Category category3 = new Category("SHOES");

        this.categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

        List<Category> categories = this.categoryRepository.findAll();

        Product product1 = new Product("Levis 501", 1399);
        product1.setCategory(category1);
        Product product2 = new Product("Lee 101", 1799);
        product2.setCategory(category1);
        Product product3 = new Product("Wrangler 13MWZ", 499);
        product3.setCategory(category1);

        Product product4 = new Product("Gant skjorta", 999);
        product4.setCategory(category2);
        Product product5 = new Product("Mormors stickade tröja", 559);
        product5.setCategory(category2);
        Product product6 = new Product("Lacoste piké", 379);
        product6.setCategory(category2);

        Product product7 = new Product("Dr Martens låg", 1999);
        product7.setCategory(category3);
        Product product8 = new Product("Adidas Gazelle", 689);
        product8.setCategory(category3);
        Product product9 = new Product("Superga sneaker", 449);
        product9.setCategory(category3);

        this.productRepository.saveAll(Arrays.asList(product1, product2, product3,
                product4, product5, product6,
                product7, product8, product9));
    }
}

