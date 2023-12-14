package catan.ceng.catanui.controller;

import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;


import java.io.IOException;


@Component
public class MainMenuController {

    SceneLoader SceneLoader = new SceneLoader();

    @FXML
    private void applyHoverStyle(MouseEvent event) {
        Button sourceButton = (Button) event.getSource();
        sourceButton.setStyle("-fx-background-color: #2980b9;");
    }

    @FXML
    private void removeHoverStyle(MouseEvent event) {
        Button sourceButton = (Button) event.getSource();
        sourceButton.setStyle("-fx-background-color: #3498db;");
    }

    @FXML
    private void showLoginPage(ActionEvent event) {
        SceneLoader.loadFXML("/fxml/login.fxml");
    }


    @FXML
    private void showSignUpPage(ActionEvent event) {
        SceneLoader.loadFXML("/fxml/signup.fxml");
    }

    @FXML
    private void showLeaderboardPage(ActionEvent event) {
        // Implement logic to show the leaderboard page
    }

    @FXML
    private void showForgotPasswordPage(ActionEvent event) {
        // Implement logic to show the forgot password page
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}