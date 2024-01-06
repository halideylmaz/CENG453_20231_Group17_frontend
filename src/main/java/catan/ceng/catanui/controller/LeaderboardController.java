package catan.ceng.catanui.controller;

import catan.ceng.catanui.entities.GameConstants;
import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.entities.PlayerMixIn;
import catan.ceng.catanui.service.RequestService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
/**
 * The `LeaderboardController` class manages the leaderboards UI, including displaying weekly, monthly, and all-time leaderboards.
 * It fetches leaderboard data from the backend, parses the JSON response, and populates the leaderboards with player information.
 *
 * <p>This class is responsible for initializing and displaying leaderboards with sample data.
 *
 * <p>Usage:
 * <pre>{@code
 * LeaderboardController leaderboardController = new LeaderboardController();
 * leaderboardController.initialize();
 * }</pre>
 *
 */
public class LeaderboardController {

    private SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private TableView<Player> weeklyLeaderboardTable;

    @FXML
    private TableView<Player> monthlyLeaderboardTable;

    @FXML
    private TableView<Player> allTimeLeaderboardTable;

    /**
     * Initializes the leaderboards with data.
     * It sets cell value factories for each column and fetches leaderboard data from the backend.
     * Weekly, monthly, and all-time leaderboards are populated.
     */
    // Method to initialize the leaderboards with sample data
    @FXML
    public void initialize() {
        // Initialize sample data
        // Set cell value factories for each column
        setCellValueFactories(weeklyLeaderboardTable);
        setCellValueFactories(monthlyLeaderboardTable);
        setCellValueFactories(allTimeLeaderboardTable);

        try {
            weeklyLeaderboardTable.getItems().setAll(parseLeaderBoardList("weekly"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            monthlyLeaderboardTable.getItems().setAll(parseLeaderBoardList("monthly"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            allTimeLeaderboardTable.getItems().setAll(parseLeaderBoardList("all"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets cell value factories for each column in the leaderboard table.
     *
     * @param leaderboardTable The leaderboard table to set cell value factories for.
     */
    // Method to set cell value factories for each column
    private void setCellValueFactories(TableView<Player> leaderboardTable) {
        TableColumn<Player, String> PlayerNameColumn = new TableColumn<>("userName");
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getColumns().setAll(PlayerNameColumn, scoreColumn);
    }

    /**
     * Parses the leaderboard data retrieved from the backend.
     *
     * @param time The time period for which to retrieve the leaderboard data (e.g., "weekly").
     * @return A list of Player objects representing the leaderboard data.
     * @throws IOException if there's an error during JSON parsing or data retrieval.
     */
    private List<Player> parseLeaderBoardList(String time) throws IOException {
        RequestService requestService = new RequestService();
        String input = requestService.getScoreboard(time);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.addMixIn(Player.class, PlayerMixIn.class);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        return mapper.readValue(input, new TypeReference<List<Player>>() {
        });
    }

    /**
     * Switches to the main menu scene when the corresponding button is clicked.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) {

        if (GameConstants.username != null) {
            sceneLoader.loadFXML("/fxml/playmenu.fxml");
        } else sceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}