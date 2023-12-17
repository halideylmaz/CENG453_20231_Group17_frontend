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


public class PlayMenuController {
    private SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private ImageView imageView = new ImageView();

    public void initialize() {
        // Load the image and set it to the ImageView
        Image image = new Image(getClass().getResourceAsStream("/images/catanlogo.png"));
        imageView.setImage(image);
    }

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

    @FXML
    private void showLeaderboardPage(ActionEvent event) {
        sceneLoader.loadFXML("/fxml/leaderboard.fxml");
    }

    @FXML
    public void switchtoMatchMaking(ActionEvent event) {
        //switch to match making scene
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}