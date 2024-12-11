package com.example.ums.DAL.Models;

import java.util.Date;

public class Order {
    String name;
    String provider;
    String state;
    Integer count;
    Double cost;
    String status;
    String id;
    Date date;
    public Order(String name, String provider, String state, Integer count, Double cost, String status,String id, Date date) {
        this.name = name;
        this.provider = provider;
        this.state = state;
        this.count = count;
        this.cost = cost;
        this.status = status;
        this.id = id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public String getState() {
        return state;
    }

    public Integer getCount() {
        return count;
    }

    public Double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
