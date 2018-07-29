package com.matchpoint.model;

import java.util.List;

/**
 * Created by Balaji on 29/7/18.
 */
public class FeeListWrapper {
    private List<Fee> feeList;

    public List<Fee> getFeeList() {
        return feeList;
    }

    public FeeListWrapper setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
        return this;
    }
}
