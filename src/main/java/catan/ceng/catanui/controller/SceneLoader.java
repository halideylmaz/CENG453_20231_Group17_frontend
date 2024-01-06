package catan.ceng.catanui.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;
import catan.ceng.catanui.entities.GameConstants;

/**
 * A utility class for loading and displaying JavaFX scenes from FXML files.
 */
public class SceneLoader {
    /**
     * Loads an FXML file and sets it as the scene for the current JavaFX stage.
     *
     * @param fxmlfilepath The path to the FXML file to load.
     */
    public void loadFXML(String fxmlfilepath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfilepath));
            Parent root = loader.load();
            Stage stage = GameConstants.stage;
            stage.setScene(new Scene(root,800,600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error dialog)
        }
    }
}