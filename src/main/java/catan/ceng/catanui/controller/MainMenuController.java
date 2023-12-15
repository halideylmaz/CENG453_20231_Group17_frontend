package catan.ceng.catanui.controller;

import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.IOException;


@Component
public class MainMenuController {

    private SceneLoader SceneLoader = new SceneLoader();

    @FXML
    private ImageView imageView = new ImageView();

    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image(getClass().getResourceAsStream("/images/catanlogo.png"));
        imageView.setImage(image);
    }

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
        SceneLoader.loadFXML("/fxml/leaderboard.fxml");
    }

    @FXML
    private void showForgotPasswordPage(ActionEvent event) {
        // Implement logic to show the forgot password page
        SceneLoader.loadFXML("/fxml/resetpassword.fxml");
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}