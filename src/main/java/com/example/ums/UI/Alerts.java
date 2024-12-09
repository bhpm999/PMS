package com.example.ums.UI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.concurrent.CancellationException;

public class Alerts {

    public static void existAlert(){
        Alert blankData = new Alert(Alert.AlertType.ERROR,"Пользователь с таким логином или паролем уже существует", ButtonType.OK);
        blankData.setTitle("Неверные данные");
        blankData.show();
    }
    public static void notExistAlert(){
        Alert blankData = new Alert(Alert.AlertType.ERROR,"Пользователь с таким логином или паролем не существует", ButtonType.OK);
        blankData.setTitle("Неверные данные");
        blankData.show();
    }

    public static void blankDataAlert(){
        Alert blankData = new Alert(Alert.AlertType.ERROR,"Незаполненные данные", ButtonType.OK);
        blankData.setTitle("Неверные данные");
        blankData.show();
    }
    public static void confirmationAlert() throws CancellationException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, " +
                "что хотите выполнить действие?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optional = confirm.showAndWait();
        if (optional.get() == ButtonType.NO)
            throw new CancellationException();
    }
    public static void isNotEditedAlert(){
        Alert isNotEdited = new Alert(Alert.AlertType.ERROR,"Вы не изменили информацию",
                ButtonType.OK);
        isNotEdited.setTitle("Не изменённые данные");
        isNotEdited.show();
    }
}