package catan.ceng.catanui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Implement login

    }

    @FXML
    public void switchToSignUp() {

        System.out.println("Switching to Sign Up Page...");
    }
}