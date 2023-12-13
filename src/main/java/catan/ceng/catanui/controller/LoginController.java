package catan.ceng.catanui.controller;

import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.helper.AlertHelper;
import catan.ceng.catanui.service.RequestService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.util.Pair;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Window owner = loginButton.getScene().getWindow();

        if (username.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }

        if (password.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        Player player = new Player();
        player.setUsername(username);
        player.setPassword(password);


        RequestService restService = new RequestService();
        boolean loggedInUser = restService.login(player);

        if (loggedInUser == false) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Username or Password is Wrong!");
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Logged IN",
                    "in");
        }

        //Player player=Player.builder().username(username).password(password).build();
        //Player result= RequestService.login(player);


    }

    @FXML
    public void switchToSignUp() {

        System.out.println("Switching to Sign Up Page...");
    }
}