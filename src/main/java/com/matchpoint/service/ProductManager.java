package com.matchpoint.service;

import com.matchpoint.model.Fee;

/**
 * Created by Prithu on 23/9/17.
 */
public interface ProductManager {
    public Fee findById(int productId);
}
