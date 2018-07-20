package com.matchpoint.model;

import org.apache.http.client.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/7/17.
 */
@Entity
@Table(name = "event",uniqueConstraints = {@UniqueConstraint(columnNames={"name", "start_date"})})
public class Event {
    //name startDate endDate location notificationDate registrationLastDate
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    @Column
    private int eventType;
    @ManyToMany
    @JoinTable(name = "event_playing_cat", joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playing_category_id", referencedColumnName = "id"))
    private List<PlayingCategory> playingCategories;
    @Column(name = "location")
    private String location;
    @Column(name = "nofication_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date notificationDate;
    @Column(name = "registration_last_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date registrationLastDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Date getRegistrationLastDate() {
        return registrationLastDate;
    }

    public void setRegistrationLastDate(Date registrationLastDate) {
        this.registrationLastDate = registrationLastDate;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public List<PlayingCategory> getPlayingCategories() {
        return playingCategories;
    }

    public void setPlayingCategories(List<PlayingCategory> playingCategories) {
        this.playingCategories = playingCategories;
    }

    public boolean isEventOpen() {
        return new Date().before(this.endDate);
    }
}
