package catan.ceng.catanui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import catan.ceng.catanui.controller.SceneLoader;

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

    private SceneLoader SceneLoader = new SceneLoader();

    public void sendResetPasswordEmail() {
        // Implement logic to send a token to the provided email
        // Update errorMessageLabel if there's an error

        // If no error, reveal the token and password fields
        try {
            //get response from server
            boolean emailSent = true;
            if (!emailSent) {
                // Show the error message
                //errorMessageLabel.setText(response.message);
                errorMessageLabel.setText("Invalid email.");
            } else {
                // Clear the error message if login is successful
                errorMessageLabel.setText("");
                // Proceed with the next steps after successful login
                // open main menu
                tokenAndPasswordBox.setVisible(true);
                emailBox.setVisible(false);
            }
        } catch (Exception e) {
            // Handle other exceptions
            errorMessageLabel.setText("An error occurred during sending email.");
        }

    }

    public void resetPassword() {
        // Implement logic to validate the token and reset the password
        // Update errorMessageLabel if there's an error
        try{
            //get response from server
            boolean resetSuccessful = true;
            if (!resetSuccessful) {
                // Show the error message
                //errorMessageLabel.setText(response.message);
                errorMessageLabel.setText("Invalid token.");
            } else {
                // Clear the error message if login is successful
                errorMessageLabel.setText("");
                // Proceed with the next steps after successful login
                // open main menu
                SceneLoader.loadFXML("/fxml/login.fxml");
            }
        } catch (Exception e) {
            // Handle other exceptions
            errorMessageLabel.setText("An error occurred during resetting password.");
        }
    }

}