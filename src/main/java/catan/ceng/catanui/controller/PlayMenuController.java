package catan.ceng.catanui.controller;
import catan.ceng.catanui.controller.SceneLoader;
import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.entities.CatanGame;
import catan.ceng.catanui.entities.CatanPlayer;
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
        List<CatanPlayer> players = new ArrayList<CatanPlayer>();
        CatanPlayer player1 = new CatanPlayer(GameConstants.username, false, "#ff0000");
        players.add(player1);
        CatanPlayer player2 = new CatanPlayer("AI#1", true, "#00ff00");
        players.add(player2);
        CatanPlayer player3 = new CatanPlayer("AI#2", true, "#0000ff");
        players.add(player3);
        CatanPlayer player4 = new CatanPlayer("AI#3", true, "#ffff00");
        players.add(player4);
        GameConstants.game = new CatanGame(players);

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