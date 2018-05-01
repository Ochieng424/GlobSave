package com.example.ochieng_derrick.globsave.models;

import java.util.Date;

/**
 * Transaction Class
 */

public class Transaction {
    private String id, accountId;
    private double amount;
    private boolean isDeposit;
    private Date time;

    public Transaction(String id, String accountId, double amount, boolean isDeposit, Date time) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.isDeposit = isDeposit;
        this.time = time;
    }

    public Transaction(){

    }

    public Transaction(String accountId, double amount, boolean isDeposit, Date time) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDeposit = isDeposit;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
