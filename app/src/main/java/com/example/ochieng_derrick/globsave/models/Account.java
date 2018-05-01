package com.example.ochieng_derrick.globsave.models;

import java.util.Date;

/**
 * Account Class
 */

public class Account {
    private String id, name;
    private double target;
    private Date targetDate;


    public Account(String id, String name, double target, Date targetDate) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.targetDate = targetDate;
    }

    public Account() {

    }

    public Account(String name, double target, Date targetDate) {
        this.name = name;
        this.target = target;
        this.targetDate = targetDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
}
