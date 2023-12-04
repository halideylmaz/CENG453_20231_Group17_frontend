package catan.ceng.catanui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    public void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        // Implement sign-up

    }

    @FXML
    public void switchToLogin() {

        System.out.println("Switching to Login Page...");
    }

}