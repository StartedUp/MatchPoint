package com.matchpoint.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Prithviprakash on 3/11/18.
 */
@Entity
public class TrainingProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal fee;
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fromDate;
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date toDate;
    @Column
    private int type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
