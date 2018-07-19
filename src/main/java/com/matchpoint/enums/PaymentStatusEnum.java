package com.matchpoint.enums;

/**
 * Created by root on 18/7/18.
 */
public enum PaymentStatusEnum {
    INIT(1,"Initialized"),
    SUCCESS(2,"Success"),
    FAILED(3, "Failed");

    private int statusCode;
    private String status;

    PaymentStatusEnum(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
