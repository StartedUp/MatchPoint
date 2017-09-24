package com.matchpoint.repository;

import com.matchpoint.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Prithu on 23/9/17.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Product findById(int productId);
}
