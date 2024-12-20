package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receipient_id")
    private UserRecord receipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private UserRecord sender;

    @CreationTimestamp
    private Date createdAt;

    private float amount;

    public TransactionRecord(UserRecord receipient, UserRecord sender, float amount) {
        this.receipient = receipient;
        this.sender = sender;
        this.amount = amount;

    }

    public TransactionRecord(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRecord getReceipient() {
        return receipient;
    }

    public void setReceipient(UserRecord receipient) {
        this.receipient = receipient;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
