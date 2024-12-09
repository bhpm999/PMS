package com.example.ums.UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.Objects;

public interface ElementsVisibility {
    default void setVisibility(AnchorPane anchorPane, String user){
        ObservableList<Node> adminPanelElements = FXCollections.observableArrayList();
        adminPanelElements.addAll(anchorPane.getChildren());
        for (Node element: adminPanelElements) {
            if(Objects.equals(element.getId(), user) || Objects.equals(element.getId(),"systemElement")){
                element.setVisible(true);
            }
            else
                element.setVisible(false);
        }
    }

    default void setVisibility(List<AnchorPane> anchorPanes, String user){
        for (AnchorPane anchorPane: anchorPanes) {
            ObservableList<Node> adminPanelElements = FXCollections.observableArrayList();
            adminPanelElements.addAll(anchorPane.getChildren());
            for (Node element: adminPanelElements) {
                if(Objects.equals(element.getId(), user) || Objects.equals(element.getId(),"systemElement")){
                    element.setVisible(true);
                }
                else
                    element.setVisible(false);
            }
        }
    }
}
