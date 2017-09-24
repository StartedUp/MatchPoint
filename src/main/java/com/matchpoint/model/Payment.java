package com.matchpoint.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Prithu on 14/9/17.
 */
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "payment_date")
    private Date paymentDate;
    @NotNull
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "transcation_id")
    private String transactionId;
    @Column(name = "orderId")
    private String orderId;
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
    @NotNull
    @Column(name="payment_status")
    private String paymentStatus;
    @Column(name = "description")
    private String description;
    @NotNull
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    @NotNull
    @OneToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public Payment setId(int id) {
        this.id = id;
        return this;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Payment setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public Payment setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Payment setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Payment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Payment setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Payment setProduct(Product product) {
        this.product = product;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Payment setUser(User user) {
        this.user = user;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public Payment setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
