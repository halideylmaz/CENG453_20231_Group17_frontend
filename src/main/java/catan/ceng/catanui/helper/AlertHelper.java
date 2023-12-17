package catan.ceng.catanui.helper;


import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * A utility class for creating and displaying alert dialogs in a JavaFX application.
 */
public class AlertHelper {

    /**
     * Displays an alert dialog with the specified alert type, title, and message.
     *
     * @param alertType The type of the alert (e.g., INFORMATION, WARNING, ERROR).
     * @param owner     The window owner for the alert dialog.
     * @param title     The title of the alert dialog.
     * @param message   The message to display in the alert dialog.
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}