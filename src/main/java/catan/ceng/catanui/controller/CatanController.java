package catan.ceng.catanui.controller;

import javafx.application.Platform;
import catan.ceng.catanui.shape.Hexagon;
import catan.ceng.catanui.shape.Road;
import catan.ceng.catanui.shape.Settlement;
import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.shape.Polygon;
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
import catan.ceng.catanui.service.RequestService;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The controller class for the Catan game, responsible for managing the game logic and user interface interactions.
 * This class handles player turns, dice rolling, resource distribution, and various player actions such as buying roads,
 * settlements, and cities. Additionally, it facilitates the transition between game phases and provides a graphical
 * representation of the game board.
 *
 * <p>The CatanController class interacts with the GameConstants, RequestService, and AlertHelper classes to manage
 * game-specific constants, communicate with a server for score updates, and display alert messages, respectively.</p>
 *
 * <p>The methods within this class handle various aspects of the game, such as starting and ending turns, buying roads,
 * settlements, and cities, and transitioning between game phases. Player actions are facilitated through the use of
 * JavaFX buttons and alerts, providing a responsive and interactive user interface.</p>
 *
 * <p>Key methods include {@code beginturn()}, {@code endTurn()}, and methods associated with buying roads, settlements,
 * and cities. These methods incorporate multithreading for AI turns and UI updates to maintain a smooth gaming experience.</p>
 *
 * <p>The controller also ensures that the game ends appropriately, updating scores on the server and displaying the winner
 * in an alert. Scene transitions are handled through the {@code switchtoplaymenu()} method, which sets the game instance
 * to null and loads the playmenu.fxml file for returning to the main menu.</p>
 *
 * <p>This class relies on a GameConstants instance to store game-related constants, a RequestService instance to interact
 * with a server for score updates, and an AlertHelper class for displaying alert messages in the UI.</p>
 *
 * @see GameConstants
 * @see RequestService
 * @see AlertHelper
 * @see CatanPlayer
 * @see Road
 * @see Settlement
 * @see javafx.scene.control.Alert
 * @see javafx.event.ActionEvent
 * @see javafx.fxml.FXML
 * @see javafx.scene.image.ImageView
 * @see javafx.scene.layout.HBox
 * @see javafx.scene.control.Label
 * @see javafx.geometry.Pos
 * @see javafx.geometry.HPos
 * @see javafx.geometry.VPos
 * @see javafx.application.Platform
 * @see java.lang.Thread
 */
@Component
public class CatanController implements Initializable {
    private static final int BOARD_SIZE = 5;
    private static final int HEXAGON_RADIUS = 55;
    private static final double SETTLEMENT_RADIUS = 10.0; // Adjust the size as needed
    private static final double ROAD_WIDTH = 4.0;
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

    public Hexagon[][] board;

    private boolean choosingRoad = false;
    private boolean choosingSettlement = false;
    private boolean choosingCity = false;
    private Pane hexagonPane;

    /**
     * Initializes the Catan game interface. If playing against AI, sets up players, initializes resources, game board,
     * and starts the first turn.
     *
     * @param url            The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resourceBundle The resources for the root object, or {@code null} if the root object was not created from an FXML file.
     */
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
                player1.initialResources();
                player2.initialResources();
                player3.initialResources();
                player4.initialResources();
                GameConstants.game = new CatanGame(players);
                initializeGameBoard();
                updateResourceInfo();
                giveRandomRoadandSettlement();
                beginturn();
            }
    }

    /**
     * Assigns random roads and settlements to players at the beginning of the game.
     * Randomly selects settlements and associated roads for each player.
     */
    private void giveRandomRoadandSettlement(){
        List<Settlement> settlements = new ArrayList<>();
        settlements.addAll(GameConstants.game.getSettlements());
        List<Road> roads = new ArrayList<>();
        roads.addAll(GameConstants.game.getRoads());
        Collections.shuffle(settlements);
        List<Settlement> randomSettlements = settlements.subList(0, 4);
        GameConstants.game.getPlayers().get(0).addSettlement(randomSettlements.get(0));
        GameConstants.game.getPlayers().get(1).addSettlement(randomSettlements.get(1));
        GameConstants.game.getPlayers().get(2).addSettlement(randomSettlements.get(2));
        GameConstants.game.getPlayers().get(3).addSettlement(randomSettlements.get(3));
        List<Road> neihbourtofsettlement0 = roads.stream().filter(road ->
                road.neighbour(randomSettlements.get(0))).collect(Collectors.toList());
        Collections.shuffle(neihbourtofsettlement0);
        GameConstants.game.getPlayers().get(0).addRoad(neihbourtofsettlement0.get(0));

        List<Road> neihbourtofsettlement1 = roads.stream().filter(road ->
                road.neighbour(randomSettlements.get(1))).collect(Collectors.toList());
        Collections.shuffle(neihbourtofsettlement1);
        int i = 0;
        Road road1 = neihbourtofsettlement1.get(0);
        while(road1.getOwner() != null){
            i++;
            road1 = neihbourtofsettlement1.get(i);
        }
        GameConstants.game.getPlayers().get(1).addRoad(road1);

        List<Road> neihbourtofsettlement2 = roads.stream().filter(road ->
                road.neighbour(randomSettlements.get(2))).collect(Collectors.toList());
        Collections.shuffle(neihbourtofsettlement2);
        i=0;
        Road road2 = neihbourtofsettlement2.get(0);
        while(road2.getOwner() != null){
            i++;
            road2 = neihbourtofsettlement2.get(i);
        }
        GameConstants.game.getPlayers().get(2).addRoad(road2);
        List<Road> neihbourtofsettlement3 = roads.stream().filter(road ->
                road.neighbour(randomSettlements.get(3))).collect(Collectors.toList());
        Collections.shuffle(neihbourtofsettlement3);
        i=0;
        Road road3 = neihbourtofsettlement3.get(0);
        while(road3.getOwner() != null){
            i++;
            road3 = neihbourtofsettlement3.get(i);
        }
        GameConstants.game.getPlayers().get(3).addRoad(road3);
    }

    /**
     * Updates the resource information in the UI, including longest road, player names, scores, cities, settlements, roads,
     * and individual resource counts for each player.
     */
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

    /**
     * Initializes the game board interface by adding hexagons, settlements, roads, and other UI elements.
     * Also sets up resource tiles, numbers, and initializes the game board's hexagon array.
     */
    @FXML
    private void initializeGameBoard() {
        hexagonPane = new Pane();
        gameBoardPane.add(hexagonPane, 0, 0);
        gameBoardPane.setHalignment(hexagonPane, HPos.CENTER);
        gameBoardPane.setValignment(hexagonPane, VPos.CENTER);
        board=new Hexagon[BOARD_SIZE][BOARD_SIZE];
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
        GameConstants.game.getSettlements().stream().forEach(settlement -> {
           settlement.getSettlement().toFront();
        });

    }

    /**
     * Adds a hexagon to the game board with the specified resource type, number, row, and column.
     *
     * @param resource      The type of resource the hexagon represents.
     * @param number        The number associated with the hexagon, indicating the roll that triggers resource collection.
     * @param row           The row index of the hexagon on the game board.
     * @param col           The column index of the hexagon on the game board.
     * @param hexagonPane   The JavaFX pane where the hexagon is added.
     */
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
        hexagonY += 60;

        hexagon.setLayoutX(hexagonX);
        hexagon.setLayoutY(hexagonY);

        // Add hexagon to the pane
        hexagonPane.getChildren().add(hexagon);
        // Add roads along the edges of the hexagon
        addAllRoads(hexagonX +33 , hexagonY + 37, hexagonPane, hexagon); // Upper-left corner
        addAllSettlements(hexagonX + 33, hexagonY + 37, hexagonPane, hexagon); // Upper-left corner
        board[row][col]=hexagon;
        GameConstants.game.addHexagon(hexagon);
    }

    /**
     * Adds all settlements to a hexagon on the game board, considering the hexagon's position.
     * Associates each settlement with the hexagon and handles mouse events for settlement purchase.
     *
     * @param hexagonX      The x-coordinate of the hexagon's center.
     * @param hexagonY      The y-coordinate of the hexagon's center.
     * @param hexagonPane   The JavaFX pane where the hexagon is located.
     * @param hexagon       The hexagon to which settlements are added.
     */
    private void addAllSettlements(double hexagonX, double hexagonY, Pane hexagonPane, Hexagon hexagon){
        double hexWidth = 43 * Math.sqrt(3);
        double hexHeight = 43 * 2;
        double x1 = hexagonX - hexWidth / 2.0;
        double y1 = hexagonY - hexHeight / 4.0;

        double x2 = hexagonX;
        double y2 = hexagonY - hexHeight / 2.0;

        double x3 = hexagonX + hexWidth / 2.0;
        double y3 = hexagonY - hexHeight / 4.0;

        double x4 = hexagonX + hexWidth / 2.0;
        double y4 = hexagonY + hexHeight / 4.0;

        double x5 = hexagonX;
        double y5 = hexagonY + hexHeight / 2.0;

        double x6 = hexagonX - hexWidth / 2.0;
        double y6 = hexagonY + hexHeight / 4.0;

        Settlement settlement1 = GameConstants.game.addSettlement(x1, y1, hexagon);
        if(settlement1 != null){
            hexagonPane.getChildren().add(settlement1.getSettlement());
            settlement1.getSettlement().setOnMouseClicked(e->{
                if(choosingSettlement) chooseSettlementtoBuy(settlement1);
                else if(choosingCity) chooseCitytoBuy(settlement1);
            });
        }
        Settlement settlement2 = GameConstants.game.addSettlement(x2, y2, hexagon);
        if(settlement2 != null){
            hexagonPane.getChildren().add(settlement2.getSettlement());
            settlement2.getSettlement().setOnMouseClicked(e->{
               if(choosingSettlement) chooseSettlementtoBuy(settlement2);
                else if(choosingCity) chooseCitytoBuy(settlement2);
            });
        }
        Settlement settlement3 = GameConstants.game.addSettlement(x3, y3, hexagon);
        if(settlement3 != null){
            hexagonPane.getChildren().add(settlement3.getSettlement());
            settlement3.getSettlement().setOnMouseClicked(e->{
                if(choosingSettlement) chooseSettlementtoBuy(settlement3);
                else if(choosingCity) chooseCitytoBuy(settlement3);
            });
        }
        Settlement settlement4 = GameConstants.game.addSettlement(x4, y4, hexagon);
        if(settlement4 != null){
            hexagonPane.getChildren().add(settlement4.getSettlement());
            settlement4.getSettlement().setOnMouseClicked(e->{
                if(choosingSettlement) chooseSettlementtoBuy(settlement4);
                else if(choosingCity) chooseCitytoBuy(settlement4);
            });
        }
        Settlement settlement5 = GameConstants.game.addSettlement(x5, y5, hexagon);
        if(settlement5 != null){
            hexagonPane.getChildren().add(settlement5.getSettlement());
            settlement5.getSettlement().setOnMouseClicked(e->{
                if(choosingSettlement) chooseSettlementtoBuy(settlement5);
                else if(choosingCity) chooseCitytoBuy(settlement5);
            });
        }
        Settlement settlement6 = GameConstants.game.addSettlement(x6, y6, hexagon);
        if(settlement6 != null){
            hexagonPane.getChildren().add(settlement6.getSettlement());
            settlement6.getSettlement().setOnMouseClicked(e->{
                if(choosingSettlement) chooseSettlementtoBuy(settlement6);
                else if(choosingCity) chooseCitytoBuy(settlement6);
            });
        }
    }

    /**
     * Adds all roads to a hexagon on the game board, considering the hexagon's position.
     * Associates each road with the hexagon and handles mouse events for road purchase.
     *
     * @param hexagonX      The x-coordinate of the hexagon's center.
     * @param hexagonY      The y-coordinate of the hexagon's center.
     * @param hexagonPane   The JavaFX pane where the hexagon is located.
     * @param hexagon       The hexagon to which roads are added.
     */
    private void addAllRoads(double hexagonX, double hexagonY, Pane hexagonPane, Hexagon hexagon) {
        double hexWidth = 43 * Math.sqrt(3);
        double hexHeight = 43 * 2;

        // Calculate coordinates for the six edges of the hexagon
        double startX1 = hexagonX - hexWidth / 2.0;
        double startY1 = hexagonY - hexHeight / 4.0;
        double endX1 = hexagonX;
        double endY1 = hexagonY - hexHeight / 2.0;

        double startX2 = hexagonX;
        double startY2 = hexagonY - hexHeight / 2.0;
        double endX2 = hexagonX + hexWidth / 2.0;
        double endY2 = hexagonY - hexHeight / 4;

        double startX3 = hexagonX + hexWidth / 2;
        double startY3 = hexagonY - hexHeight / 4;
        double endX3 = hexagonX + hexWidth / 2;
        double endY3 = hexagonY + hexHeight / 4;

        double startX4 = hexagonX + hexWidth / 2;
        double startY4 = hexagonY + hexHeight / 4;
        double endX4 = hexagonX;
        double endY4 = hexagonY + hexHeight / 2;

        double startX5 = hexagonX;
        double startY5 = hexagonY + hexHeight / 2;
        double endX5 = hexagonX - hexWidth / 2;
        double endY5 = hexagonY + hexHeight / 4;

        double startX6 = hexagonX - hexWidth / 2;
        double startY6 = hexagonY + hexHeight / 4;
        double endX6 = hexagonX - hexWidth / 2;
        double endY6 = hexagonY - hexHeight / 4;

        // Add roads for all six edges
        Road road1 = GameConstants.game.addRoad( startX1, startY1, endX1, endY1, hexagon);
         if(road1!= null) {
             hexagonPane.getChildren().add(road1.getLine());
             road1.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road1);
             });
         };
        Road road2 = GameConstants.game.addRoad( startX2, startY2, endX2, endY2, hexagon);
        if(road2!=null)    {hexagonPane.getChildren().add(road2.getLine());
            road2.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road2);
            });
        }

        Road road3 = GameConstants.game.addRoad( startX3, startY3, endX3, endY3, hexagon);
        if(road3!=null)  {  hexagonPane.getChildren().add(road3.getLine());
            road3.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road3);
            });
        }

        Road road4 = GameConstants.game.addRoad( startX4, startY4, endX4, endY4, hexagon);
        if(road4!=null)  {  hexagonPane.getChildren().add(road4.getLine());
            road4.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road4);
            });
        }

        Road road5 = GameConstants.game.addRoad( startX5, startY5, endX5, endY5, hexagon);
        if(road5!=null)   { hexagonPane.getChildren().add(road5.getLine());
            road5.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road5);
            });
        }

        Road road6 = GameConstants.game.addRoad( startX6, startY6, endX6, endY6, hexagon);
        if(road6!=null)  { hexagonPane.getChildren().add(road6.getLine());
            road6.getLine().setOnMouseClicked(e -> {
                chooseRoadtoBuy(road6);
            });
        }

    }

    /**
     * Calculates the number of columns based on the given row for hexagon layout.
     *
     * @param row The row index on the game board.
     * @return The number of columns corresponding to the given row.
     */
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

    /**
     * Simulates rolling two dice, displays the rolled dice images on the game board,
     * and returns the sum of the dice.
     *
     * @return The sum of the two rolled dice.
     */
    private int handleRollDice() {
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

    /**
     * Ends the game, identifies the winner, updates scores to the server,
     * and displays an information alert with the winner's name.
     */
    private void endGame() {
        CatanPlayer winner = GameConstants.game.getPlayerwithHighestScore();
        Window owner = GameConstants.stage.getScene().getWindow();
        //update scores here to server
        RequestService restService = new RequestService();
        GameConstants.game.getPlayers().stream()
                .filter(player -> !player.isAI())
                .forEach(player -> {
                    restService.addScore(player.getPlayerName(), player.getScore().toString());
                });

        if (winner != null) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // Handle InterruptedException, if needed
                    System.out.println("Interrupted");
                }
                Platform.runLater(() -> {
                    // Perform UI updates here
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Game Over!", "The winner is " + winner.getPlayerName() + "!");
                    sceneLoader.loadFXML("/fxml/playmenu.fxml");
                });


            }).start();
        }
    }

    /**
     * Initiates a player's turn, handling various game actions such as rolling dice,
     * distributing resources, updating longest road, and processing AI turns.
     * If the game is over, it triggers the endGame() method.
     */
    private void beginturn(){
        choosingCity=false;
        choosingSettlement=false;
        choosingRoad=false;
        int sum = handleRollDice();
        if (sum == 7) {
            //do nothing
        } else {
            //give resources according to player's settlements and cities
            GameConstants.game.handleResources(sum);
        }
        GameConstants.game.updateLongestRoad();
        updateResourceInfo();
        if(GameConstants.game.isGameOver()){
            endGame();
            return;
        }
        CatanPlayer player = GameConstants.game.getCurrentPlayer();
        if(player.isAI()){
            new Thread(() -> {
                player.automateTurn();
                System.out.println("AI turn ended");
                Platform.runLater(() -> {
                    // Perform UI updates here
                    player.getSettlementsList().stream().filter(s -> s.isCity()).forEach(s -> {
                        if(!hexagonPane.getChildren().contains(s.getCity())){
                            hexagonPane.getChildren().remove(s.getSettlement());
                            hexagonPane.getChildren().add(s.getCity());

                        }

                    });
                    endTurn();
                });


            }).start();
        }
    }

    /**
     * Ends the current player's turn and initiates the next turn by calling beginturn().
     */
    private void endTurn(){
        GameConstants.game.endTurn();
        beginturn();
    }

    /**
     * Handles the action when the end turn button is pressed.
     * If it is the player's turn, it calls the endTurn() method.
     * Otherwise, it shows an error alert indicating that it's not the player's turn.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void endturnbutton(ActionEvent event) {
        Window owner = GameConstants.stage.getScene().getWindow();
        if(GameConstants.game.getCurrentPlayer().getPlayerName().equals(GameConstants.username)){
            endTurn();
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the action when the buy road button is pressed.
     * If it is the player's turn, it shows an information alert to choose a road to buy and sets choosingRoad to true.
     * Otherwise, it shows an error alert indicating that it's not the player's turn.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void buyRoadbutton(ActionEvent event) {
        Window owner = GameConstants.stage.getScene().getWindow();
        if(GameConstants.game.getCurrentPlayer().getPlayerName().equals(GameConstants.username)){
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Choose Road",
                    "Choose a road to buy");
            choosingRoad = true;
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the action when the buy settlement button is pressed.
     * If it is the player's turn, it shows an information alert to choose a settlement to buy and sets choosingSettlement to true.
     * Otherwise, it shows an error alert indicating that it's not the player's turn.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void buySettlementbutton(ActionEvent event) {
        Window owner = GameConstants.stage.getScene().getWindow();
        if(GameConstants.game.getCurrentPlayer().getPlayerName().equals(GameConstants.username)){
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Choose Settlement",
                    "Choose a settlement to buy");
            choosingSettlement = true;
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the action when the buy city button is pressed.
     * If it is the player's turn, it shows an information alert to choose a settlement to upgrade to a city and sets choosingCity to true.
     * Otherwise, it shows an error alert indicating that it's not the player's turn.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void buyCitybutton(ActionEvent event) {
        Window owner = GameConstants.stage.getScene().getWindow();
        if(GameConstants.game.getCurrentPlayer().getPlayerName().equals(GameConstants.username)){
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Choose City",
                    "Choose a settlement to upgrade to city");
            choosingCity = true;
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the selection of a road for purchase during the player's turn.
     * If choosingRoad is true, it attempts to build the road using the player's buildRoad method.
     * Shows appropriate alert messages based on the success or failure of the road construction.
     *
     * @param road The Road object selected for purchase.
     */
    public void chooseRoadtoBuy(Road road) {
        if(!choosingRoad){
            return;
        }
        Window owner = GameConstants.stage.getScene().getWindow();
        CatanPlayer player = GameConstants.game.getCurrentPlayer();
        if(player.getPlayerName().equals(GameConstants.username)){
            if(player.buildRoad(road)){
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Road bought",
                        "Road bought successfully");
                updateResourceInfo();
            }
            else{
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Road can not bought",
                        "Road can not bought");
            }
            choosingRoad = false;
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the selection of a settlement for purchase during the player's turn.
     * If choosingSettlement is true, it attempts to build the settlement using the player's buildSettlement method.
     * Shows appropriate alert messages based on the success or failure of the settlement construction.
     *
     * @param settlement The Settlement object selected for purchase.
     */
    public void chooseSettlementtoBuy(Settlement settlement) {
        if(!choosingSettlement){
            return;
        }
        Window owner = GameConstants.stage.getScene().getWindow();
        CatanPlayer player = GameConstants.game.getCurrentPlayer();
        if (player.getPlayerName().equals(GameConstants.username)) {
            if (player.buildSettlement(settlement)) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Settlement bought",
                        "Settlement bought successfully");
                updateResourceInfo();
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Settlement can not bought",
                        "Settlement can not bought");
            }
            choosingSettlement = false;
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Handles the selection of a settlement for upgrading to a city during the player's turn.
     * If choosingCity is true, it attempts to build the city using the player's buildCity method.
     * Shows appropriate alert messages based on the success or failure of the city upgrade.
     *
     * @param settlement The Settlement object selected for upgrading to a city.
     */
    public void chooseCitytoBuy(Settlement settlement) {
        if(!choosingCity){
            return;
        }
        Window owner = GameConstants.stage.getScene().getWindow();
        CatanPlayer player = GameConstants.game.getCurrentPlayer();
        if (player.getPlayerName().equals(GameConstants.username)) {
            if (player.buildCity(settlement)) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "City bought",
                        "City bought successfully");
                hexagonPane.getChildren().remove(settlement.getSettlement());
                hexagonPane.getChildren().add(settlement.getCity());
                updateResourceInfo();
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "City can not bought",
                        "City can not bought");
            }
            choosingCity = false;
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "It is not your turn",
                    "Please wait for your turn");
        }
    }

    /**
     * Switches the scene to the play menu, setting the GameConstants.game to null.
     * Loads the playmenu.fxml file to switch the view.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void switchtoplaymenu(ActionEvent event) {
        GameConstants.game = null;
       sceneLoader.loadFXML("/fxml/playmenu.fxml");
    }
}
