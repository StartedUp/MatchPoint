package com.matchpoint.service;

import com.matchpoint.model.Product;

/**
 * Created by Prithu on 23/9/17.
 */
public interface ProductManager {
    public Product findById(int productId);
}
