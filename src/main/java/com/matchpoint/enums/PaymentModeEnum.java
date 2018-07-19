package com.matchpoint.enums;

/**
 * Created by root on 18/7/18.
 */
public enum PaymentModeEnum {
    CASH(1,"Cash"),
    ONLINE(2,"Online"),
    CHEQUE(3, "Cheque");

    private int mode;
    private String description;

    PaymentModeEnum(int mode, String description) {
        this.mode = mode;
        this.description = description;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
