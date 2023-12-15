package catan.ceng.catanui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;


@Component
public class LoginController {
    private SceneLoader SceneLoader = new SceneLoader();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Implement login

        try{
            //get response from server
            boolean loginSuccessful = true;
            if (!loginSuccessful) {
                // Show the error message
                //errorMessageLabel.setText(response.message);
                errorMessageLabel.setText("Invalid username or password.");
            } else {
                // Clear the error message if login is successful
                errorMessageLabel.setText("");
                // Proceed with the next steps after successful login
                // open play scene
                SceneLoader.loadFXML("/fxml/playmenu.fxml");

            }
        } catch (Exception e) {
            // Handle other exceptions
            errorMessageLabel.setText("An error occurred during login.");
        }
    }

    @FXML
    public void switchToSignUp(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/signup.fxml");
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}