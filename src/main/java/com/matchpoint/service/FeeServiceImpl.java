package com.matchpoint.service;

import com.matchpoint.model.Fee;
import com.matchpoint.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Balaji on 27/7/18.
 */
@Service
public class FeeServiceImpl implements FeeService {
    @Autowired
    private FeeRepository feeRepository;
    @Override
    public List<Fee> listFee() {
        return feeRepository.findAll();
    }

    @Override
    public Fee addFee(Fee fee) {
        return feeRepository.save(fee);
    }

    @Override
    public Fee updateFee(Fee fee) {
        return feeRepository.save(fee);
    }

    @Override
    public void removeFee(int id) {
        feeRepository.delete(id);
    }

    @Override
    public Fee getFeeById(int id) {
        return feeRepository.findOne(id);
    }

    @Override
    public List<Fee> findByPlayerCategoryId(int playerCategory) {
        return feeRepository.findByPlayerCategoryId(playerCategory);
    }




}
