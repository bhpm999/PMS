package com.example.ums.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.ums.DAL.db.DB.connectToDatabase;

public class HelloApplication extends Application {
    @Override
    public void  start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("/com/example/ums/chooseDBpage.fxml"));
        Scene chooseDB = new Scene(fxmlLoader1.load());
        stage.setScene(chooseDB);
        stage.show();
    }

    public static void main(String[] args) {
        connectToDatabase();



        launch();
    }
}
