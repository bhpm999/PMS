package com.example.ums.DAL.Interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public interface ICRUD {
     default void create(Map<String,Object> params){}
     default <T> ObservableList<T> read(){
          return FXCollections.observableArrayList();
     };
     default void update(Map<String,Object> params){};
     default void delete(Map<String,Object> params){};

}
