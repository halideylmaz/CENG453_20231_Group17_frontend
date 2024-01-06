package catan.ceng.catanui.controller;

import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.helper.AlertHelper;
import catan.ceng.catanui.service.RequestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
/**
 * The `SignUpController` class is responsible for controlling the sign-up form UI and handling user sign-up actions.
 * It validates user input, communicates with the backend for user registration, and navigates between scenes.
 *
 * <p>This class is marked as a Spring component using the {@code @Component} annotation, allowing it to be managed
 * by the Spring application context.
 *
 * <p>Usage:
 * <pre>{@code
 * SignUpController signUpController = new SignUpController();
 * signUpController.handleSignUp();
 * }</pre>
 *
 * @see org.springframework.stereotype.Component
 */
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
    private Button signUpButton;

    /**
     * Handles the sign-up action when the "Sign Up" button is clicked.
     * It validates user input, registers the user, and navigates to the main menu upon successful registration.
     */
    @FXML
    public void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        Window owner = signUpButton.getScene().getWindow();
        if (username.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        if (email.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }

        if (password.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        if (password.length() < 5) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Your password must have at least 5 characters");
            return;
        }
        // Implement sign-up
        Player player = new Player();
        player.setUserName(username);
        player.setEmail(email);
        player.setPassword(password);


        RequestService restService = new RequestService();
        boolean registeredUser = restService.register(player);

        if (!registeredUser) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Error on sign up please try again!");
        } else {
            SceneLoader.loadFXML("/fxml/mainmenu.fxml");
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Register succesfull!",
                    "Signed Up, you can login");
        }
    }

    /**
     * Switches to the login scene when the "Login" button is clicked.
     *
     * @param event The action event triggered by clicking the "Login" button.
     */
    @FXML
    public void switchToLogin(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/login.fxml");
    }

    /**
     * Switches to the main menu scene when the "Main Menu" button is clicked.
     *
     * @param event The action event triggered by clicking the "Main Menu" button.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) {

        SceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }

}