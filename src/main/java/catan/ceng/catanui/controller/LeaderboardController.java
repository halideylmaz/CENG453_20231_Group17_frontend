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

    // Method to set cell value factories for each column
    private void setCellValueFactories(TableView<Player> leaderboardTable) {
        TableColumn<Player, String> PlayerNameColumn = new TableColumn<>("userName");
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getColumns().setAll(PlayerNameColumn, scoreColumn);
    }

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

    @FXML
    public void switchToMainMenu(ActionEvent event) {

        if (GameConstants.username != null) {
            sceneLoader.loadFXML("/fxml/playmenu.fxml");
        } else sceneLoader.loadFXML("/fxml/mainmenu.fxml");
    }
}