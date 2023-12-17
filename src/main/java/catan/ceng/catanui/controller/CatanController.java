package catan.ceng.catanui.controller;

import javafx.application.Platform;
import catan.ceng.catanui.shape.Hexagon;
import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.event.ActionEvent;
import catan.ceng.catanui.entities.CatanGame;
import catan.ceng.catanui.entities.CatanPlayer;
import catan.ceng.catanui.helper.AlertHelper;



@Component
public class CatanController implements Initializable {
    private static final int BOARD_SIZE = 5;
    private static final int HEXAGON_RADIUS = 55;
    private SceneLoader sceneLoader = new SceneLoader();
    @FXML
    private BorderPane mainPane;

    @FXML
    public GridPane gameBoardPane;

    @FXML
    private Label player1UsernameLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player1CitiesLabel;
    @FXML
    private Label player1SettlementsLabel;
    @FXML
    private Label player1RoadsLabel;
    @FXML
    private Label player2UsernameLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private Label player2CitiesLabel;
    @FXML
    private Label player2SettlementsLabel;
    @FXML
    private Label player2RoadsLabel;
    @FXML
    private Label player3UsernameLabel;
    @FXML
    private Label player3ScoreLabel;
    @FXML
    private Label player3CitiesLabel;
    @FXML
    private Label player3SettlementsLabel;
    @FXML
    private Label player3RoadsLabel;
    @FXML
    private Label player4UsernameLabel;
    @FXML
    private Label player4ScoreLabel;
    @FXML
    private Label player4CitiesLabel;
    @FXML
    private Label player4SettlementsLabel;
    @FXML
    private Label player4RoadsLabel;
    @FXML
    private Label player1ResourcesLabel;
    @FXML
    private Label player2ResourcesLabel;
    @FXML
    private Label player3ResourcesLabel;
    @FXML
    private Label player4ResourcesLabel;

    @FXML
    private Label player1BrickLabel;
    @FXML
    private Label player1GrainLabel;
    @FXML
    private Label player1LumberLabel;
    @FXML
    private Label player1OreLabel;
    @FXML
    private Label player1WoolLabel;
    @FXML
    private Label player2BrickLabel;
    @FXML
    private Label player2GrainLabel;
    @FXML
    private Label player2LumberLabel;
    @FXML
    private Label player2OreLabel;
    @FXML
    private Label player2WoolLabel;
    @FXML
    private Label player3BrickLabel;
    @FXML
    private Label player3GrainLabel;
    @FXML
    private Label player3LumberLabel;
    @FXML
    private Label player3OreLabel;
    @FXML
    private Label player3WoolLabel;
    @FXML
    private Label player4BrickLabel;
    @FXML
    private Label player4GrainLabel;
    @FXML
    private Label player4LumberLabel;
    @FXML
    private Label player4OreLabel;
    @FXML
    private Label player4WoolLabel;

    @FXML
    private Label playerTurnLabel;
    @FXML
    private Label longestroad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            if (GameConstants.vsAI) {
                List<CatanPlayer> players = new ArrayList<CatanPlayer>();
                CatanPlayer player1 = new CatanPlayer(GameConstants.username, false, "#8B0000");
                players.add(player1);
                CatanPlayer player2 = new CatanPlayer("AI#1", true, "#000080");
                players.add(player2);
                CatanPlayer player3 = new CatanPlayer("AI#2", true, "#006400");
                players.add(player3);
                CatanPlayer player4 = new CatanPlayer("AI#3", true, "#8B4513");
                players.add(player4);
                GameConstants.game = new CatanGame(players);
                initializeGameBoard();
                updateResourceInfo();
                beginturn();
            }
    }

    private void initializeDices() {
    }

    private void updateResourceInfo() {
        if(GameConstants.game.getPlayerwithLongestRoad() == null){
            longestroad.setText("No one has longest road yet");
        }
        else{
            longestroad.setText(GameConstants.game.getPlayerwithLongestRoad().getPlayerName() + " has longest road with " + GameConstants.game.getLongestRoad() + " roads");
        }
        player1UsernameLabel.setText( GameConstants.game.getPlayers().get(0).getPlayerName());
        player1UsernameLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(0).getColor() + ";");
        player1ScoreLabel.setText("Score: " + GameConstants.game.getPlayers().get(0).getScore());
        player1CitiesLabel.setText("Cities: " + GameConstants.game.getPlayers().get(0).getCities());
        player1SettlementsLabel.setText("Settlements: " + GameConstants.game.getPlayers().get(0).getSettlements());
        player1RoadsLabel.setText("Roads: " + GameConstants.game.getPlayers().get(0).getRoads());

        player2UsernameLabel.setText( GameConstants.game.getPlayers().get(1).getPlayerName());
        player2UsernameLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(1).getColor() + ";");
        player2ScoreLabel.setText("Score: " + GameConstants.game.getPlayers().get(1).getScore());
        player2CitiesLabel.setText("Cities: " + GameConstants.game.getPlayers().get(1).getCities());
        player2SettlementsLabel.setText("Settlements: " + GameConstants.game.getPlayers().get(1).getSettlements());
        player2RoadsLabel.setText("Roads: " + GameConstants.game.getPlayers().get(1).getRoads());

        player3UsernameLabel.setText( GameConstants.game.getPlayers().get(2).getPlayerName());
        player3UsernameLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(2).getColor() + ";");
        player3ScoreLabel.setText("Score: " + GameConstants.game.getPlayers().get(2).getScore());
        player3CitiesLabel.setText("Cities: " + GameConstants.game.getPlayers().get(2).getCities());
        player3SettlementsLabel.setText("Settlements: " + GameConstants.game.getPlayers().get(2).getSettlements());
        player3RoadsLabel.setText("Roads: " + GameConstants.game.getPlayers().get(2).getRoads());

        player4UsernameLabel.setText( GameConstants.game.getPlayers().get(3).getPlayerName());
        player4UsernameLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(3).getColor() + ";");
        player4ScoreLabel.setText("Score: " + GameConstants.game.getPlayers().get(3).getScore());
        player4CitiesLabel.setText("Cities: " + GameConstants.game.getPlayers().get(3).getCities());
        player4SettlementsLabel.setText("Settlements: " + GameConstants.game.getPlayers().get(3).getSettlements());
        player4RoadsLabel.setText("Roads: " + GameConstants.game.getPlayers().get(3).getRoads());

        player1ResourcesLabel.setText(GameConstants.game.getPlayers().get(0).getPlayerName() + "'s Resources");
        player1ResourcesLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(0).getColor() + "; -fx-font-size: 8;");
        player2ResourcesLabel.setText(GameConstants.game.getPlayers().get(1).getPlayerName() + "'s Resources");
        player2ResourcesLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(1).getColor() + "; -fx-font-size: 8;");
        player3ResourcesLabel.setText(GameConstants.game.getPlayers().get(2).getPlayerName() + "'s Resources");
        player3ResourcesLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(2).getColor() + "; -fx-font-size: 8;");
        player4ResourcesLabel.setText(GameConstants.game.getPlayers().get(3).getPlayerName() + "'s Resources");
        player4ResourcesLabel.setStyle("-fx-text-fill: " + GameConstants.game.getPlayers().get(3).getColor() + "; -fx-font-size: 8;");

        player1BrickLabel.setText("Brick: " + GameConstants.game.getPlayers().get(0).getResourceCount(ResourceType.BRICK));
        player1GrainLabel.setText("Grain: " + GameConstants.game.getPlayers().get(0).getResourceCount(ResourceType.GRAIN));
        player1LumberLabel.setText("Lumber: " + GameConstants.game.getPlayers().get(0).getResourceCount(ResourceType.LUMBER));
        player1OreLabel.setText("Ore: " + GameConstants.game.getPlayers().get(0).getResourceCount(ResourceType.ORE));
        player1WoolLabel.setText("Wool: " + GameConstants.game.getPlayers().get(0).getResourceCount(ResourceType.WOOL));
        player2BrickLabel.setText("Brick: " + GameConstants.game.getPlayers().get(1).getResourceCount(ResourceType.BRICK));
        player2GrainLabel.setText("Grain: " + GameConstants.game.getPlayers().get(1).getResourceCount(ResourceType.GRAIN));
        player2LumberLabel.setText("Lumber: " + GameConstants.game.getPlayers().get(1).getResourceCount(ResourceType.LUMBER));
        player2OreLabel.setText("Ore: " + GameConstants.game.getPlayers().get(1).getResourceCount(ResourceType.ORE));
        player2WoolLabel.setText("Wool: " + GameConstants.game.getPlayers().get(1).getResourceCount(ResourceType.WOOL));
        player3BrickLabel.setText("Brick: " + GameConstants.game.getPlayers().get(2).getResourceCount(ResourceType.BRICK));
        player3GrainLabel.setText("Grain: " + GameConstants.game.getPlayers().get(2).getResourceCount(ResourceType.GRAIN));
        player3LumberLabel.setText("Lumber: " + GameConstants.game.getPlayers().get(2).getResourceCount(ResourceType.LUMBER));
        player3OreLabel.setText("Ore: " + GameConstants.game.getPlayers().get(2).getResourceCount(ResourceType.ORE));
        player3WoolLabel.setText("Wool: " + GameConstants.game.getPlayers().get(2).getResourceCount(ResourceType.WOOL));
        player4BrickLabel.setText("Brick: " + GameConstants.game.getPlayers().get(3).getResourceCount(ResourceType.BRICK));
        player4GrainLabel.setText("Grain: " + GameConstants.game.getPlayers().get(3).getResourceCount(ResourceType.GRAIN));
        player4LumberLabel.setText("Lumber: " + GameConstants.game.getPlayers().get(3).getResourceCount(ResourceType.LUMBER));
        player4OreLabel.setText("Ore: " + GameConstants.game.getPlayers().get(3).getResourceCount(ResourceType.ORE));
        player4WoolLabel.setText("Wool: " + GameConstants.game.getPlayers().get(3).getResourceCount(ResourceType.WOOL));
        playerTurnLabel.setText(GameConstants.game.getCurrentPlayer().getPlayerName() + "'s turn");
        playerTurnLabel.setStyle("-fx-text-fill: " + GameConstants.game.getCurrentPlayer().getColor() + "; -fx-font-size: 30;");

    }

    @FXML
    private void initializeGameBoard() {
        Pane hexagonPane = new Pane();
        gameBoardPane.add(hexagonPane, 0, 0);
        gameBoardPane.setHalignment(hexagonPane, HPos.CENTER);
        gameBoardPane.setValignment(hexagonPane, VPos.CENTER);
        //board=new Hexagon[BOARD_SIZE][BOARD_SIZE];
        // Define the resource tiles and their corresponding numbers
        List<String> resources = new ArrayList<>();
        resources.addAll(Collections.nCopies(3, "Hill"));
        resources.addAll(Collections.nCopies(3, "Mountain"));
        resources.addAll(Collections.nCopies(4, "Forest"));
        resources.addAll(Collections.nCopies(4, "Field"));
        resources.addAll(Collections.nCopies(4, "Pasture"));

        List<Integer> numbers = new ArrayList<>(List.of(2, 3, 3, 11, 11, 12, 4, 5, 6, 8, 9, 10, 4, 5, 6, 8, 9, 10));
        // Desert has no number

        // Shuffle the arrays to randomize resource and number placements
        Collections.shuffle(resources);
        Collections.shuffle(numbers);

        int row = 0;
        int col = 0;

        for (int i = 0; i <= 17; i++) {
            if (i == 9) {
                addHexagon("Desert", -1, row, col, hexagonPane);
                col++;
            }

            addHexagon(resources.get(i), numbers.get(i), row, col, hexagonPane);
            col++;
            if (col == calculateCols(row)) {
                col = 0;
                row++;
            }
        }
        ImageView imageViewDie1=new ImageView(new Image(getClass().getResourceAsStream("/images/dice/die6.png")));
        Label plus=new Label("+");
        ImageView imageViewDie2=new ImageView(new Image(getClass().getResourceAsStream("/images/dice/die6.png")));

        HBox hbox=new HBox();
        hbox.getChildren().addAll(imageViewDie1, plus, imageViewDie2);
        hbox.setAlignment(Pos.CENTER);
        gameBoardPane.add(hbox, 0, 1);
        gameBoardPane.setHalignment(hbox, HPos.CENTER);
        gameBoardPane.setValignment(hbox, VPos.CENTER);

    }
    private void addHexagon(String resource, int number, int row, int col, Pane hexagonPane) {
        Hexagon hexagon = new Hexagon(resource, number);


        double hexWidth = HEXAGON_RADIUS * Math.sqrt(3);
        double hexHeight = HEXAGON_RADIUS * 2;


        double boardWidth = calculateCols(row) * hexWidth * 0.75;
        double boardHeight =hexHeight * 0.75;

        double centerX = hexagonPane.getWidth() / 2 - boardWidth / 2;
        double centerY = hexagonPane.getHeight() / 2 - boardHeight / 2;

        double hexagonX;
        if(row==0 || row==4){
            hexagonX = centerX + (col+2) * hexWidth *0.8;
        }
        else if(row==1 || row==3){
            hexagonX = centerX + (col+1) * hexWidth * 0.8;
        }
        else {

            hexagonX = centerX + (col+2) * hexWidth *0.8 ;
        }


        if (row % 2 != 0) {
            hexagonX += hexWidth * 0.8;
        }

        double hexagonY = centerY + row * (hexHeight * 0.6);


        hexagon.setLayoutX(hexagonX);
        hexagon.setLayoutY(hexagonY + 10);

        // Add hexagon to the pane
        hexagonPane.getChildren().add(hexagon);
        //add hexagon and related information to the board array
        GameConstants.game.board[row][col]=hexagon;
    }

    private int calculateCols(int row) {
        switch (row) {
            case 0:
            case 4:
                return 3;
            case 1:
            case 3:
                return 4;
            case 2:
                return 5;
            default:
                return 0;
        }
    }

    public int handleRollDice() {
        /* TODO if player's turn */
        int die1= (int) (Math.random()*6)+1;
        int die2= (int) (Math.random()*6)+1;

        ImageView imageViewDie1=new ImageView(new Image(getClass().getResourceAsStream("/images/dice/die"+die1+".png")));
        ImageView imageViewDie2=new ImageView(new Image(getClass().getResourceAsStream("/images/dice/die"+die2+".png")));

        HBox hbox=new HBox();
        Label plus=new Label("+");
        hbox.getChildren().addAll(imageViewDie1,plus, imageViewDie2);
        hbox.setAlignment(Pos.CENTER);
        gameBoardPane.add(hbox, 0, 1);
        gameBoardPane.setHalignment(hbox, HPos.CENTER);
        gameBoardPane.setValignment(hbox, VPos.CENTER);
        return die1+die2;
    }


    public void beginturn(){
        updateResourceInfo();
        int sum = handleRollDice();
        if (sum == 7) {
            //handleSeven();
        } else {
            //handleResources(sum);
        }
        CatanPlayer player = GameConstants.game.getCurrentPlayer();
        if(player.isAI()){
            new Thread(() -> {
                player.automateTurn();
                System.out.println("AI turn ended");
                Platform.runLater(() -> {
                    // Perform UI updates here
                    endTurn();
                });


            }).start();
        }
        else{
            //currentPlayer.beginTurn();
        }
    }

    public void endTurn(){
        GameConstants.game.endTurn();
        beginturn();
    }

    @FXML
    public void endturnbutton(ActionEvent event) {
        if(GameConstants.game.getCurrentPlayer().getPlayerName().equals(GameConstants.username)){
            endTurn();
        }
        else{
            Window owner = GameConstants.stage.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    @FXML
    public void switchtoplaymenu(ActionEvent event) {
        GameConstants.game = null;
       sceneLoader.loadFXML("/fxml/playmenu.fxml");
    }
}
