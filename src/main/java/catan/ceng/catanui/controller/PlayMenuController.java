package catan.ceng.catanui.controller;
import catan.ceng.catanui.controller.SceneLoader;
import catan.ceng.catanui.entities.GameConstants;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * The controller class for the play menu screen in a JavaFX application.
 * It handles actions and interactions related to the play menu screen, including switching to other scenes.
 */
public class PlayMenuController {
    private SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private ImageView imageView = new ImageView();

    /**
     * Initializes the play menu controller.
     * This method is called after the FXML file has been loaded and the fields have been injected.
     * It loads and sets an image to the ImageView.
     */
    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image(getClass().getResourceAsStream("/images/catanlogo.png"));
        imageView.setImage(image);
    }

    /**
     * Handles the action to switch to the game scene.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void switchtoGame(ActionEvent event) {
        //switch to game scene
        if(GameConstants.username==null){
            sceneLoader.loadFXML("/fxml/login.fxml");
            return;
        }
        GameConstants.vsAI = true;

        sceneLoader.loadFXML("/fxml/main.fxml");
    }

    /**
     * Handles the action to show the leaderboard page.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void showLeaderboardPage(ActionEvent event) {
        sceneLoader.loadFXML("/fxml/leaderboard.fxml");
    }

    /**
            * Handles the action to switch to the match-making scene.
            *
            * @param event The ActionEvent triggered by the button click.
            */
    @FXML
    public void switchtoMatchMaking(ActionEvent event) {
        //switch to match making scene
    }
    /**
     * Handles the action to exit the application.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}