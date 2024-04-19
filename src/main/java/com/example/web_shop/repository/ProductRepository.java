package com.example.web_shop.repository;

import com.example.web_shop.model.Category;
import com.example.web_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContaining(String keyword);
    List<Product> findAllByCategoryName(String category);
}
