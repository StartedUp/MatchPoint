package com.matchpoint.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Prithu on 14/9/17.
 */
@Entity
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty
    @Column(name="product_name", nullable=false)
    private String productName;
    @Column
    private PlayerCategory playerCategory;
    @NotEmpty
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name="description")
    private String description;

    public int getId() {
        return id;
    }

    public Fee setId(int id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Fee setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Fee setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Fee setDescription(String description) {
        this.description = description;
        return this;
    }

    public PlayerCategory getPlayerCategory() {
        return playerCategory;
    }

    public void setPlayerCategory(PlayerCategory playerCategory) {
        this.playerCategory = playerCategory;
    }
}
