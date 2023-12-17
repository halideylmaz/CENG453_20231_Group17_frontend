package catan.ceng.catanui.controller;

import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.helper.AlertHelper;
import catan.ceng.catanui.service.RequestService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

/**
 * The `LoginController` class is responsible for controlling the login UI and handling user login actions.
 * It provides functionality for validating user credentials and navigating to different scenes upon successful login.
 *
 * <p>This class is typically used for user authentication in the application.
 *
 * <p>Usage:
 * <pre>{@code
 * LoginController loginController = new LoginController();
 * loginController.handleLogin();
 * }</pre>
 *
 * @see org.springframework.stereotype.Component
 */
@Component
public class LoginController {
    private SceneLoader SceneLoader = new SceneLoader();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    /**
     * Handles the user login action.
     * Validates the entered username and password, communicates with the backend service for authentication,
     * and navigates to the appropriate scene upon successful login.
     */
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
        player.setUserName(username);
        player.setPassword(password);


        RequestService restService = new RequestService();
        boolean loggedInUser = restService.login(player);

        if (!loggedInUser) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Username or Password is Wrong!");
        } else {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful!",
                    "You are logged in");
            SceneLoader.loadFXML("/fxml/playmenu.fxml");
            GameConstants.username = username;
        }

    }

    /**
     * Switches to the sign-up scene when the corresponding button is clicked.
     */
    @FXML
    public void switchToSignUp(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/signup.fxml");
    }

    /**
     * Switches to the main menu scene when the corresponding button is clicked.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}