package catan.ceng.catanui.controller;
import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.helper.AlertHelper;
import catan.ceng.catanui.service.RequestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.stage.Window;

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