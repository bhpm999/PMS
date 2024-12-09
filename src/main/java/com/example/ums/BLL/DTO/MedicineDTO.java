package com.example.ums.BLL.DTO;

public class MedicineDTO  {
    String group;
    String name;
    int count;
    double cost;
    String provider;
    String state;

    public MedicineDTO(String group, String name, int count, double cost, String provider, String state) {

        this.group = group;
        this.name = name;
        this.count = count;
        this.cost = cost;
        this.provider = provider;
        this.state = state;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getCost() {
        return cost;
    }

    public String getProvider() {
        return provider;
    }

    public String getState() {
        return state;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setState(String state) {
        this.state = state;
    }
}
