package com.clinic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    public Button loginButton;

    public void openLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/Login.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) loginButton.getScene().getWindow();

        stage.setScene(scene);
    }
}