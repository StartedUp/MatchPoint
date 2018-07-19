package com.matchpoint.enums;

/**
 * Created by Prithu on 19/7/18.
 */
public enum GenderTypeEnum {
    MALE(1,"Male"),
    FEMALE(2,"Female"),
    OTHERS(3, "Others"),
    ALL (4, "All");

    private int value;
    private String description;

    GenderTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public GenderTypeEnum setValue(int value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GenderTypeEnum setDescription(String description) {
        this.description = description;
        return this;
    }

}
