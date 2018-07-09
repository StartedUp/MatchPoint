package com.matchpoint.enums;

/**
 * Created by root on 6/6/18.
 */
public enum EventTypesEnum {
    OPEN(1,"Open", true),
    INTERNAL(2,"Internal", true);

    private int eventType;
    private String eventTypeName;
    private boolean isActiveEvent;

    EventTypesEnum(int eventType, String eventTypeName, boolean isActiveEvent) {
        this.eventType = eventType;
        this.eventTypeName = eventTypeName;
        this.isActiveEvent = isActiveEvent;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public boolean isActiveEvent() {
        return isActiveEvent;
    }

    public void setActiveEvent(boolean activeEvent) {
        isActiveEvent = activeEvent;
    }
}
