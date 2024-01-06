package catan.ceng.catanui.controller;
import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.helper.AlertHelper;
import catan.ceng.catanui.service.RequestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.stage.Window;

/**
 * The `ResetPasswordController` class is responsible for controlling the password reset UI and handling password
 * reset actions. It provides functionality for sending a reset password email, validating the token, and updating
 * the password.
 *
 * <p>This class is typically used to facilitate the process of resetting a user's password in the application.
 *
 * <p>Usage:
 * <pre>{@code
 * ResetPasswordController resetPasswordController = new ResetPasswordController();
 * resetPasswordController.sendResetPasswordEmail();
 * }</pre>
 *
 */

public class ResetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField tokenField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private VBox tokenAndPasswordBox;

    @FXML
    private VBox emailBox;

    @FXML
    private Button submitButton;
    private SceneLoader SceneLoader = new SceneLoader();

    /**
     * Sends a reset password email to the provided email address.
     * Validates the email input and displays appropriate error messages or proceeds to the token and password input.
     */
    public void sendResetPasswordEmail() {
        String email = emailField.getText();
        Window owner = submitButton.getScene().getWindow();

        if (email.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }

        RequestService restService = new RequestService();
        boolean resetPassword = restService.sendResetEmail(email);

        if (!resetPassword) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Invalid Email",
                    "Username or Password is Wrong!");
        } else {
            tokenAndPasswordBox.setVisible(true);
            emailBox.setVisible(false);
        }


    }

    /**
     * Resets the user's password using the provided token and new password.
     * Validates the token and password input and displays appropriate error messages or navigates to the login scene.
     */
    public void resetPassword() {
        // Implement logic to validate the token and reset the password
        // Update errorMessageLabel if there's an error

        String token = tokenField.getText();
        String newPassword = newPasswordField.getText();
        Window owner = submitButton.getScene().getWindow();

        if (token.isEmpty() || newPassword.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Token or Password is empty!");
            return;
        }

        RequestService restService = new RequestService();
        boolean resetPassword = restService.resetPassword(token, newPassword);

        if (!resetPassword) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Invalid token");
        } else {
            SceneLoader.loadFXML("/fxml/login.fxml");
        }
    }

}