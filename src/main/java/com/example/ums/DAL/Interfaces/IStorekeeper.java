package com.example.ums.DAL.Interfaces;

import com.example.ums.DAL.Models.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface IStorekeeper extends ICRUD {
     default ObservableList<Medicine> readMedicine(){
        return FXCollections.observableArrayList();
    }
}
