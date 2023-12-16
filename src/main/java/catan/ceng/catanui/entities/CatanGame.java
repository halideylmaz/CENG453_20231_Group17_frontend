package catan.ceng.catanui.entities;
import catan.ceng.catanui.shape.Hexagon;
import java.util.ArrayList;
import java.util.List;
import catan.ceng.catanui.entities.CatanPlayer;
import catan.ceng.catanui.controller.CatanController;


public class CatanGame {
    private static final int BOARD_SIZE = 5;
    private List<CatanPlayer> players;
    private int currentPlayerIndex;
    public Hexagon[][] board;

    public CatanGame(List<CatanPlayer> players) {
        this.players = players;
        currentPlayerIndex = 0; // Set the starting player index
        board=new Hexagon[BOARD_SIZE][BOARD_SIZE];
    }

    public CatanPlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player's turn
    }

    // Implement methods for game actions, such as rolling dice, collecting resources, trading, building, etc.

    public boolean isGameOver() {
        // Implement logic to check if the game is over (e.g., a player has reached a certain number of victory points)
        for (CatanPlayer player : players) {
            if (player.getScore() >= 8) {
                return true;
            }
        }
        return false; // Placeholder; replace with actual logic
    }
}