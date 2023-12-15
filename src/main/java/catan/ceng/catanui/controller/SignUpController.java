package catan.ceng.catanui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import javafx.event.ActionEvent;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.scene.control.Label;

@Component
public class SignUpController {

    private SceneLoader SceneLoader = new SceneLoader();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        // Implement sign-up
        try{
            //get response from server
            boolean registerSuccessful = true;
            if (!registerSuccessful) {
                // Show the error message
                //errorMessageLabel.setText(response.message);
                errorMessageLabel.setText("Invalid username or password.");
            } else {
                // Clear the error message if login is successful
                errorMessageLabel.setText("");
                // Proceed with the next steps after successful login
                // open main menu
                SceneLoader.loadFXML("/fxml/mainmenu.fxml");

            }
        } catch (Exception e) {
            // Handle other exceptions
            errorMessageLabel.setText("An error occurred during login.");
        }
    }

    @FXML
    public void switchToLogin(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/login.fxml");
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }

}