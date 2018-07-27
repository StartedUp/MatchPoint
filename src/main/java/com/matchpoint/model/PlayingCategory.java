package com.matchpoint.model;

import com.matchpoint.enums.GenderTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 6/6/18.
 */
@Entity
public class PlayingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column
    private BigDecimal fee;
    @Column
    private boolean active;
    @Column(columnDefinition="int default 0")
    private int genderType; //GenderTypeEnum
    @ManyToMany(mappedBy = "playingCategories")
    private List<Event> events;
    @ManyToMany(mappedBy = "playingCategories")
    private List<EventRegistration> eventRegistrations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<EventRegistration> getEventRegistrations() {
        return eventRegistrations;
    }

    public void setEventRegistrations(List<EventRegistration> eventRegistrations) {
        this.eventRegistrations = eventRegistrations;
    }

    public int getGenderType() {
        return genderType;
    }

    public void setGenderType(int genderType) {
        this.genderType = genderType;
    }

    public GenderTypeEnum getGenderType(int value){
        List<GenderTypeEnum> genderTypes = Arrays.asList(GenderTypeEnum.values());
        for(GenderTypeEnum genderType:genderTypes){
            if(value==genderType.getValue()){
                return genderType;
            }
        }
        return null;
    }
}