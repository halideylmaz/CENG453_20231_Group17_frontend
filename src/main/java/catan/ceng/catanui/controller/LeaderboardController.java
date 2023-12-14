package catan.ceng.catanui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleIntegerProperty;

public class LeaderboardController {

    private SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private TableView<Player> weeklyLeaderboardTable;

    @FXML
    private TableView<Player> monthlyLeaderboardTable;

    @FXML
    private TableView<Player> allTimeLeaderboardTable;

    // Method to initialize the leaderboards with sample data
    @FXML
    public void initialize() {
        // Initialize sample data
        ObservableList<Player> weeklyLeaderboardData = getSamplePlayerData();
        ObservableList<Player> monthlyLeaderboardData = getSamplePlayerData();
        ObservableList<Player> allTimeLeaderboardData = getSamplePlayerData();
        // Set cell value factories for each column
        setCellValueFactories(weeklyLeaderboardTable);
        setCellValueFactories(monthlyLeaderboardTable);
        setCellValueFactories(allTimeLeaderboardTable);

        // Set data for each table
        weeklyLeaderboardTable.setItems(weeklyLeaderboardData);
        monthlyLeaderboardTable.setItems(monthlyLeaderboardData);
        allTimeLeaderboardTable.setItems(allTimeLeaderboardData);
    }

    // Method to set cell value factories for each column
    private void setCellValueFactories(TableView<Player> leaderboardTable) {
        TableColumn<Player, String> playerNameColumn = new TableColumn<>("Username");
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getColumns().setAll(playerNameColumn, scoreColumn);
    }

    // Method to generate sample player data
    private ObservableList<Player> getSamplePlayerData() {
        ObservableList<Player> players = FXCollections.observableArrayList();
        players.add(new Player("Player1sdfsadflsdfsjdfksdjfkjskdjskdjskdjskdjskdjskdjsk", 100));
        players.add(new Player("Player2", 90));
        players.add(new Player("Player3", 80));
        players.add(new Player("Player4", 70));
        // Add more sample players as needed
        return players;
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        sceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}