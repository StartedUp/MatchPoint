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
public class TrainingProgramPayment {
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
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "fee_id")
    private Fee fee;
    @OneToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public TrainingProgramPayment setId(int id) {
        this.id = id;
        return this;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public TrainingProgramPayment setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public TrainingProgramPayment setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TrainingProgramPayment setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TrainingProgramPayment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public TrainingProgramPayment setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TrainingProgramPayment setDescription(String description) {
        this.description = description;
        return this;
    }

    public Fee getFee() {
        return fee;
    }

    public TrainingProgramPayment setFee(Fee fee) {
        this.fee = fee;
        return this;
    }

    public User getUser() {
        return user;
    }

    public TrainingProgramPayment setUser(User user) {
        this.user = user;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public TrainingProgramPayment setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
