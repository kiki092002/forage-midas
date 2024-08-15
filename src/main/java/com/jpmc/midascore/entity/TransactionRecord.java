package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class TransactionRecord {
    @Id
    private Long id;
    private float amount;
    @ManyToOne
    @JoinColumn(name = "senderID")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipientID")
    private UserRecord recipient;
    public TransactionRecord(){}

    public TransactionRecord(Long id, UserRecord sender, UserRecord recipient, float amount){
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public void setAmount(float amount) {
        this.amount= amount;
    }


}
