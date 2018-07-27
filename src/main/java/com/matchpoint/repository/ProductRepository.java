package com.matchpoint.repository;

import com.matchpoint.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Prithu on 23/9/17.
 */
public interface ProductRepository extends JpaRepository<Fee, Integer> {
    public Fee findById(int productId);
}
