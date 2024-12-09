package com.example.ums.DAL.Interfaces;

import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface IClient extends ICRUD{

    default ObservableList<Order> readOrder(){
        return FXCollections.observableArrayList();
    }
    default  ObservableList<Medicine> readMedicine(){
        return FXCollections.observableArrayList();
    }
}
