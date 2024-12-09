package com.example.ums.UI;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

public interface Tables {
    default <T> void setCellProperty(Map<String, TableColumn<T,String>> map){
        for (TableColumn<T, String> column: map.values()) {
            for(Map.Entry<String, TableColumn<T, String>> entry:map.entrySet()){
                if(column.equals(entry.getValue())){
                    column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
                }
            }
        }
    }


}
