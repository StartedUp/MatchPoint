package com.matchpoint.service;

import com.matchpoint.model.Fee;

import java.util.List;

/**
 * Created by Prithu on 27/7/18.
 */
public interface FeeService {
    List<Fee> listFee();

    Fee addFee(Fee fee);

    Fee updateFee(Fee fee);

    void removeFee(int id);

    Fee getFeeById(int id);

    List<Fee> findByPlayerCategoryId(int playerCategory);
}
