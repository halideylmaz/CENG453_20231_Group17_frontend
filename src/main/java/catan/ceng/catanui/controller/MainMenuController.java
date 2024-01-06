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

/**
 * The controller class for the main menu screen in a JavaFX application.
 * It handles actions and interactions related to the main menu screen, such as loading different scenes.
 */
@Component
public class MainMenuController {

    private SceneLoader SceneLoader = new SceneLoader();

    @FXML
    private ImageView imageView = new ImageView();

    /**
     * Initializes the main menu controller.
     * This method is called after the FXML file has been loaded, and the fields have been injected.
     * It loads and sets an image of Catan logo to the ImageView.
     */
    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image(getClass().getResourceAsStream("/images/catanlogo.png"));
        imageView.setImage(image);
    }

    /**
     * Handles the action to show the login page.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void showLoginPage(ActionEvent event) {
        SceneLoader.loadFXML("/fxml/login.fxml");
    }

    /**
     * Handles the action to show the sign-up page.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void showSignUpPage(ActionEvent event) {
        SceneLoader.loadFXML("/fxml/signup.fxml");
    }

    /**
     * Handles the action to show the leaderboard page.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void showLeaderboardPage(ActionEvent event) {
        SceneLoader.loadFXML("/fxml/leaderboard.fxml");
    }

    /**
     * Handles the action to show the forgot password page.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void showForgotPasswordPage(ActionEvent event) {
        // Implement logic to show the forgot password page
        SceneLoader.loadFXML("/fxml/resetpassword.fxml");
    }

    /**
     * Handles the action to exit the application.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}