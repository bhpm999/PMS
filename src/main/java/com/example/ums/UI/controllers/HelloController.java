package com.example.ums.UI.controllers;

import com.example.ums.BLL.services.LogINService;
import com.example.ums.UI.Alerts;
import com.example.ums.UI.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CancellationException;

public class HelloController {
    public TextField loginField;
    public PasswordField passwordField;
    public Button enterButton;
    public Hyperlink registrationLink;
    public TextField newLogin;
    public TextField newPassword;
    public Hyperlink enterLink;
    public Button registrButton;
    public Text whichDBText;
    public Hyperlink chooseDBLink;
    public Text choosedDBText;

    public void initialize(){
        switch (LogINService.db){
            case MySQL -> whichDBText.setText("MySQL");
            case mongoDB -> whichDBText.setText("MongoDB");
        }
    }
    public void enter () {
        LogINService logIN = new LogINService();
        if(!logIN.enterSystem(loginField.getText(), passwordField.getText())) {
            return;
        }
        else {
            enterButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/com/example/ums/home.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 1007, 400);
                Stage stage = new Stage();
                stage.setTitle("Pharmacy Management System");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void registrateLink(){
        loginField.setVisible(false);
        passwordField.setVisible(false);
        enterButton.setVisible(false);
        registrationLink.setVisible(false);
        newLogin.setVisible(true);
        newPassword.setVisible(true);
        enterLink.setVisible(true);
        registrButton.setVisible(true);
        whichDBText.setVisible(false);
        chooseDBLink.setVisible(false);
        choosedDBText.setVisible(false);

    }
    public void EnterLink(){
        loginField.setVisible(true);
        passwordField.setVisible(true);
        enterButton.setVisible(true);
        enterLink.setVisible(true);
        registrationLink.setVisible(true);
        registrButton.setVisible(false);
        newLogin.setVisible(false);
        newPassword.setVisible(false);
        enterLink.setVisible(false);
        whichDBText.setVisible(true);
        chooseDBLink.setVisible(true);
        choosedDBText.setVisible(true);
    }
    public void setRegistrButton(){
        try{
            if(newLogin.getText().isEmpty() || newPassword.getText().isEmpty()) {
                Alerts.blankDataAlert();
            }else {
                Alerts.confirmationAlert();
                LogINService adduser = new LogINService();
                if(adduser.registrate(newLogin.getText().trim(), newPassword.getText().trim())) {
                    newLogin.setText("");
                    newPassword.setText("");
                }
            }

        }
        catch (CancellationException e){
            return;
        }
    }
    public void setChooseDBLink() throws IOException {
        chooseDBLink.getScene().getWindow().hide();
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("/com/example/ums/chooseDBpage.fxml"));
        Stage stage = new Stage();
        Scene chooseDB = new Scene(fxmlLoader1.load());
        stage.setScene(chooseDB);
        stage.show();
    }

}