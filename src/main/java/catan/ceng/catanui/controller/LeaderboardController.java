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
import catan.ceng.catanui.entities.GameConstants;

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
        TableColumn<Player, String> PlayerNameColumn = new TableColumn<>("Playername");
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Playername"));

        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));

        leaderboardTable.getColumns().setAll(PlayerNameColumn, scoreColumn);
    }

    // Method to generate sample Player data
    private ObservableList<Player> getSamplePlayerData() {
        ObservableList<Player> Players = FXCollections.observableArrayList();
        // Add more sample Players as needed
        return Players;
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        if(GameConstants.username != null){
            sceneLoader.loadFXML("/fxml/playmenu.fxml");
        }
        else sceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}