package com.matchpoint.service;

import com.matchpoint.model.Product;
import com.matchpoint.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Prithu on 23/9/17.
 */
@Service
@Transactional
public class ProductManagerImpl implements ProductManager{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product findById(int productId) {
        return productRepository.findById(productId);
    }
}
