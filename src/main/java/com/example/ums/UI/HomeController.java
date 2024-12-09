package com.example.ums.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeController extends Initialization implements ElementsVisibility {

    public AnchorPane contentArea;
    public Button exitButton;
    public AnchorPane mainContentAreaPage;
    public Label userLabel;
    public AnchorPane leftAnchor;
    List<AnchorPane> anchorPanes = new ArrayList<>();

    public void initialize(){
        anchorPanes.add(leftAnchor);
        anchorPanes.add(mainContentAreaPage);
        initialization();
    }
    public void clientLog(){
        userLabel.setText("Клиент");
        setVisibility(anchorPanes,"client");
    }
    public void adminLog(){
        userLabel.setText("Админ");
        setVisibility(anchorPanes,"admin");
    }

    public void directorLog(){
        userLabel.setText("Директор");
        setVisibility(anchorPanes,"director");
    }
    public void storekeeperLog(){
        userLabel.setText("Кладовщик");
        setVisibility(anchorPanes,"storeKeeper");
    }
    public void pharmacistLog(){
        userLabel.setText("Аптекарь");
        setVisibility(anchorPanes,"pharmacist");
    }
    public void exit(ActionEvent actionEvent){
        try{
            exitButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/ums/hello-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("University Management System");
            stage.show();}
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void returnHome(){
        contentArea.getChildren().removeAll(contentArea.getChildren());
        contentArea.getChildren().add(mainContentAreaPage);
    }
    public void loadPage(String page){
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ums/" + page)));
            contentArea.getChildren().removeAll(contentArea.getChildren());
            contentArea.getChildren().add(fxml);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void addingPage(){
        loadPage("addingPage.fxml");
    }
    public void viewInfoAboutTeacher(){
        loadPage("viewInfoAboutTeacher.fxml");
    }
    public void deletingPage(){loadPage("deletingPage.fxml");}

}