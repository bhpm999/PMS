package com.example.ums.UI;

import com.example.ums.BLL.Service.LogINBLL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseDBController {
    public RadioButton MySQLButton;
    public RadioButton mongoDBBButton;
    public Button setDBButton;
    ToggleGroup toggleGroup = new ToggleGroup();
    public void initialize(){
        MySQLButton.setToggleGroup(toggleGroup);
        mongoDBBButton.setToggleGroup(toggleGroup);
    }
    public void setSetDBButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/ums/hello-view.fxml"));
        Stage stage = new Stage();



                if (MySQLButton.isSelected()) {
                    LogINBLL.db = DBs.MySQL;
                    Scene scene = new Scene(fxmlLoader.load(), 280, 400);
                    stage.setTitle("Pharmacy Management System");
                    stage.setScene(scene);
                    stage.show();
                    setDBButton.getScene().getWindow().hide();
                } else if (mongoDBBButton.isSelected()) {
                    LogINBLL.db = DBs.mongoDB;
                    Scene scene = new Scene(fxmlLoader.load(), 280, 400);
                    stage.setTitle("Pharmacy Management System");
                    stage.setScene(scene);
                    stage.show();
                    setDBButton.getScene().getWindow().hide();
                } else {
                    Alerts.blankDataAlert();
                }


    }


}
