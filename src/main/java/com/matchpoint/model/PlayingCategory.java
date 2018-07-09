package com.matchpoint.model;

import javax.persistence.*;
import java.math.BigDecimal;

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

}
