package com.matchpoint.model;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.ws.rs.DefaultValue;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/7/17.
 */
@Entity
@Table(name = "event_registration",uniqueConstraints={@UniqueConstraint(columnNames={"playerEmail", "event_id"})})
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String playerName;
    @Column
    private String playerEmail;
    @Column
    private String playerMobile;
    @Column(columnDefinition="int default 0")
    private int gender;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "event_payment_id")
    private EventPayment eventPayment;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "user_dob")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date userDob;
    @ManyToMany
    @JoinTable(name = "event_reg_playing_cat", joinColumns = @JoinColumn(name = "event_registration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playing_category_id", referencedColumnName = "id"))
    private List<PlayingCategory> playingCategories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getUserDob() {
        return userDob;
    }

    public void setUserDob(Date userDob) {
        this.userDob = userDob;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public EventPayment getEventPayment() {
        return eventPayment;
    }

    public void setEventPayment(EventPayment eventPayment) {
        this.eventPayment = eventPayment;
    }

    public List<PlayingCategory> getPlayingCategories() {
        return playingCategories;
    }

    public void setPlayingCategories(List<PlayingCategory> playingCategories) {
        this.playingCategories = playingCategories;
    }

    public String getPlayerMobile() {
        return playerMobile;
    }

    public void setPlayerMobile(String playerMobile) {
        this.playerMobile = playerMobile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "EventRegistration{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                '}';
    }
}
