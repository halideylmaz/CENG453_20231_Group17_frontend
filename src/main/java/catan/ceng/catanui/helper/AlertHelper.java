package catan.ceng.catanui.helper;


import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * A utility class for creating and displaying alert dialogs in a JavaFX application.
 */
public class AlertHelper {

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}