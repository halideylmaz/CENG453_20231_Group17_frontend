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
    private Button loginButton;

    @FXML
    private Label errorMessageLabel;

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

        //for now
        SceneLoader.loadFXML("/fxml/playmenu.fxml");

        RequestService restService = new RequestService();
        boolean loggedInUser = restService.login(player);

        if (loggedInUser == false) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Username or Password is Wrong!");
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Logged IN",
                    "in");
            SceneLoader.loadFXML("/fxml/playmenu.fxml");
        }

        //Player player=Player.builder().username(username).password(password).build();
        //Player result= RequestService.login(player);


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