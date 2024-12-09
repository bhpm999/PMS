package com.example.ums.UI;

import com.example.ums.BLL.Service.LogINBLL;

public class Initialization {
    private int roleIndex = LogINBLL.roleID;
    public Roles role;
    private void roleIndexation(){
        switch (roleIndex){
            case 0:
                role = Roles.client;
                break;
            case 100:
                role = Roles.admin;
                break;
            case 1:
                role = Roles.storeKeeper;
                break;
            case 3:
                role = Roles.pharmacist;
                break;
            case 4:
                role = Roles.director;
                break;
        }
    }
    protected void initialization(){
        roleIndexation();
        switch (role){
            case client:
                clientLog();
                break;
            case admin:
                adminLog();
                break;
            case storeKeeper:
                storekeeperLog();
                break;
            case pharmacist:
                pharmacistLog();
                break;
            case director:
                directorLog();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }
    public void clientLog(){}
    public void adminLog(){}
    public void storekeeperLog(){}
    public void pharmacistLog(){}
    public void directorLog(){}
}
