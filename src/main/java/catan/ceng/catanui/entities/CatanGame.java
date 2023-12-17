package catan.ceng.catanui.entities;
import catan.ceng.catanui.shape.Hexagon;
import java.util.ArrayList;
import java.util.List;
import catan.ceng.catanui.entities.CatanPlayer;
import catan.ceng.catanui.controller.CatanController;
import catan.ceng.catanui.enums.ResourceType;

public class CatanGame {
    private static final int BOARD_SIZE = 5;
    private List<CatanPlayer> players;
    private int currentPlayerIndex;
    private int playerwithlongestroadIndex = -1;
    public Hexagon[][] board;
    public int longestRoad;

    public CatanGame(List<CatanPlayer> players) {
        this.players = players;
        currentPlayerIndex = 0; // Set the starting player index
        longestRoad = 5;
        board= new Hexagon[BOARD_SIZE][BOARD_SIZE];
    }

    public void setBoard(Hexagon[][] board){
        this.board=board;
    }

    public Hexagon[][] getBoard(){
        return board;
    }

    public void setPlayers(List<CatanPlayer> players){
        this.players=players;
    }

    public List<CatanPlayer> getPlayers(){
        return players;
    }

    public CatanPlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public CatanPlayer getPlayerwithHighestScore() {
        CatanPlayer playerwithHighestScore = players.get(0);
        for (CatanPlayer player : players) {
            if (player.getScore() > playerwithHighestScore.getScore()) {
                playerwithHighestScore = player;
            }
        }
        return playerwithHighestScore;
    }

    public CatanPlayer getPlayerwithLongestRoad() {
        if(playerwithlongestroadIndex == -1){
            return null;
        }
        else return players.get(playerwithlongestroadIndex);
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public CatanPlayer getPlayerbyUsername(String username){
        for(CatanPlayer player:players){
            if(player.getPlayerName().equals(username)){
                return player;
            }
        }
        return null;
    }

    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player's turn
    }

    public boolean isGameOver() {
        for (CatanPlayer player : players) {
            if (player.getScore() >= 8) {
                return true;
            }
        }
        return false;
    }
}