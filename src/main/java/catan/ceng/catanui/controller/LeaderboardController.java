package catan.ceng.catanui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import catan.ceng.catanui.entities.User;
import catan.ceng.catanui.controller.SceneLoader;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleIntegerProperty;

public class LeaderboardController {

    private SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private TableView<User> weeklyLeaderboardTable;

    @FXML
    private TableView<User> monthlyLeaderboardTable;

    @FXML
    private TableView<User> allTimeLeaderboardTable;

    // Method to initialize the leaderboards with sample data
    @FXML
    public void initialize() {
        // Initialize sample data
        ObservableList<User> weeklyLeaderboardData = getSampleUserData();
        ObservableList<User> monthlyLeaderboardData = getSampleUserData();
        ObservableList<User> allTimeLeaderboardData = getSampleUserData();
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
    private void setCellValueFactories(TableView<User> leaderboardTable) {
        TableColumn<User, String> UserNameColumn = new TableColumn<>("Username");
        UserNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));

        leaderboardTable.getColumns().setAll(UserNameColumn, scoreColumn);
    }

    // Method to generate sample User data
    private ObservableList<User> getSampleUserData() {
        ObservableList<User> Users = FXCollections.observableArrayList();
        Users.add(new User("User1sdfsadflsdfsjdfksdjfkjskdjskdjskdjskdjskdjskdjsk", 100));
        Users.add(new User("User2", 90));
        Users.add(new User("User3", 80));
        Users.add(new User("User4", 70));
        // Add more sample Users as needed
        return Users;
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        sceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}