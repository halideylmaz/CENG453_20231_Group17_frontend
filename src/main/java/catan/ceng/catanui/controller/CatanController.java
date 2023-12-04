package catan.ceng.catanui.controller;


import catan.ceng.catanui.Hexagon;
import catan.ceng.catanui.HexagonXY;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


@Component
public class CatanController implements Initializable {
    private static final int BOARD_SIZE = 5;
    private static final int HEXAGON_RADIUS = 50;
    @FXML
    private BorderPane mainPane;

    @FXML
    public GridPane gameBoardPane;

    private HexagonXY[][] board;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGameBoard();
    }

    private void initializeGameBoard() {
        Pane hexagonPane = new Pane();
        gameBoardPane.add(hexagonPane, 0, 0);
        board=new HexagonXY[BOARD_SIZE][BOARD_SIZE];
        // Define the resource tiles and their corresponding numbers
        List<String> resources = new ArrayList<>();
        resources.addAll(Collections.nCopies(3, "Hill"));
        resources.addAll(Collections.nCopies(3, "Mountain"));
        resources.addAll(Collections.nCopies(4, "Forest"));
        resources.addAll(Collections.nCopies(4, "Field"));
        resources.addAll(Collections.nCopies(4, "Pasture"));
        //resources.add("Desert");

        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(List.of(2, 3, 11, 12, 4, 5, 6,7, 8, 9, 10, 4, 5, 6,7, 8, 9, 10));
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
            hexagonX = centerX + (col+2) * hexWidth * 0.75;
        }
        else if(row==1 || row==3){
            hexagonX = centerX + (col+1) * hexWidth * 0.75;
        }
        else {

            hexagonX = centerX + (col+2) * hexWidth * 0.75;
        }


        if (row % 2 != 0) {
            hexagonX += hexWidth * 0.75;
        }

        double hexagonY = centerY + row * (hexHeight * 0.75);


        hexagon.setLayoutX(hexagonX);
        hexagon.setLayoutY(hexagonY);

        // Add hexagon to the pane
        hexagonPane.getChildren().add(hexagon);
        //add hexagon and related information to the board array
        board[row][col]=new HexagonXY(resource,number,hexagonX,hexagonY);
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

    @FXML
    public void handleRollDice() {
        // Implement dice rolling
    }

    @FXML
    public void handleBuildRoad() {
        // Implement logic for building roads
    }

    @FXML
    public void handleBuildSettlement() {
        // Implement logic for building settlements
    }

    @FXML
    public void handleBuildCity() {
        // Implement logic for building cities
    }

    @FXML
    public void showLoginPage() {
        loadPage("/fxml/login.fxml");
    }

    @FXML
    public void showSignUpPage() {
        loadPage("/fxml/signup.fxml");
    }

    private void loadPage(String fxmlFileName) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent page = loader.load();
            mainPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
