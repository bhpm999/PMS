package com.example.ums.DAL.repositories;

import com.example.ums.DAL.Models.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface StorekeeperRepository extends Repository {
     default ObservableList<Medicine> readMedicine(){
        return FXCollections.observableArrayList();
    }
}
