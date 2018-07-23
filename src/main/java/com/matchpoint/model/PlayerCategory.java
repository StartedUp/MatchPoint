package com.matchpoint.model;

import javax.persistence.*;

/**
 * Created by Balaji on 24/7/18.
 */
@Entity
public class PlayerCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String categoryName;
    @Column
    private boolean active;

    public int getId() {
        return id;
    }

    public PlayerCategory setId(int id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public PlayerCategory setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public PlayerCategory setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return "PlayerCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", active=" + active +
                '}';
    }
}
