package com.matchpoint.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.ws.rs.DefaultValue;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 8/7/17.
 */
@Entity
@Table(name = "event_registration",uniqueConstraints={@UniqueConstraint(columnNames={"user_id", "event_id"})})
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotNull
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "user_dob")
    @NotNull
    private Date userDob;
    private List<Integer> selectedPlayingCategories;
    @Column(name = "nms")
    private boolean nms;
    @Column(name = "nmd")
    private boolean nmd;
    @Column(name = "ms")
    private boolean ms;
    @Column(name = "ws")
    private boolean ws;
    @Column(name = "mcb")
    private boolean mcb;
    @Column(name = "mcg")
    private boolean mcg;
    @Column(name = "cb")
    private boolean cb;
    @Column(name = "cg")
    private boolean cg;
    @Column(name = "sjb")
    private boolean sjb;
    @Column(name = "sjg")
    private boolean sjg;
    @Column(name = "jb")
    private boolean jb;
    @Column(name = "jg")
    private boolean jg;
    @Column(name = "yb")
    private boolean yb;
    @Column(name = "yg")
    private boolean yg;

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

    public boolean isNms() {
        return nms;
    }

    public void setNms(boolean nms) {
        this.nms = nms;
    }

    public boolean isNmd() {
        return nmd;
    }

    public void setNmd(boolean nmd) {
        this.nmd = nmd;
    }

    public boolean isMs() {
        return ms;
    }

    public void setMs(boolean ms) {
        this.ms = ms;
    }

    public boolean isWs() {
        return ws;
    }

    public void setWs(boolean ws) {
        this.ws = ws;
    }

    public boolean isMcb() {
        return mcb;
    }

    public void setMcb(boolean mcb) {
        this.mcb = mcb;
    }

    public boolean isMcg() {
        return mcg;
    }

    public void setMcg(boolean mcg) {
        this.mcg = mcg;
    }

    public boolean isCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    public boolean isCg() {
        return cg;
    }

    public void setCg(boolean cg) {
        this.cg = cg;
    }

    public boolean isSjb() {
        return sjb;
    }

    public void setSjb(boolean sjb) {
        this.sjb = sjb;
    }

    public boolean isSjg() {
        return sjg;
    }

    public void setSjg(boolean sjg) {
        this.sjg = sjg;
    }

    public boolean isJb() {
        return jb;
    }

    public void setJb(boolean jb) {
        this.jb = jb;
    }

    public boolean isJg() {
        return jg;
    }

    public void setJg(boolean jg) {
        this.jg = jg;
    }

    public boolean isYb() {
        return yb;
    }

    public void setYb(boolean yb) {
        this.yb = yb;
    }

    public boolean isYg() {
        return yg;
    }

    public void setYg(boolean yg) {
        this.yg = yg;
    }

    public List<Integer> getPlayingCategories() {
        return selectedPlayingCategories;
    }

    public void setPlayingCategories(List<Integer> playingCategories) {
        this.selectedPlayingCategories = playingCategories;
    }

    @Override
    public String toString() {
        return "EventRegistration{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", userDob=" + userDob +
                ", nms=" + nms +
                ", nmd=" + nmd +
                ", ms=" + ms +
                ", ws=" + ws +
                ", mcb=" + mcb +
                ", mcg=" + mcg +
                ", cb=" + cb +
                ", cg=" + cg +
                ", sjb=" + sjb +
                ", sjg=" + sjg +
                ", jb=" + jb +
                ", jg=" + jg +
                ", yb=" + yb +
                ", yg=" + yg +
                '}';
    }
}
