package com.example.ums.DAL.repositories;

import com.example.ums.DAL.Models.User;
import com.example.ums.DAL.Models.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public interface AdminRepository extends ICRUD {
    default ObservableList<User> readUser(){
        return FXCollections.observableArrayList();
    };
    default ObservableList<Worker> readWorker(Map<String,Object> params){
        return FXCollections.observableArrayList();
    }
}
