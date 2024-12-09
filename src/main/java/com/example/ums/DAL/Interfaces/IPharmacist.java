package com.example.ums.DAL.Interfaces;

import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface IPharmacist extends ICRUD{
    default ObservableList<Medicine> readMedicines(){
        return FXCollections.observableArrayList();
    }
    default ObservableList<Order> readOrders(){
        return FXCollections.observableArrayList();
    }
}
