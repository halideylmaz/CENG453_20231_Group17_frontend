package catan.ceng.catanui.entities;
import catan.ceng.catanui.shape.Hexagon;
import java.util.ArrayList;
import java.util.List;
import catan.ceng.catanui.entities.CatanPlayer;
import catan.ceng.catanui.controller.CatanController;
import catan.ceng.catanui.enums.ResourceType;
import catan.ceng.catanui.shape.Road;
import catan.ceng.catanui.shape.Settlement;

public class CatanGame {
    private static final int BOARD_SIZE = 5;
    private List<CatanPlayer> players;
    private int currentPlayerIndex;
    private int playerwithlongestroadIndex = -1;
    public Hexagon[][] board;
    private List<Road> roads = new ArrayList<>();
    private List<Settlement> settlements = new ArrayList<>();
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

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public void updateLongestRoad(){
        int max = 0;
        CatanPlayer playerwithlongestroad = null;
        for(CatanPlayer player : players){
            if(player.getRoads() > max && player.getRoads() > longestRoad){
                max = player.getRoads();
                playerwithlongestroad = player;
            }
        }
        if(playerwithlongestroad != null){
            getPlayerwithLongestRoad().setScore(getPlayerwithLongestRoad().getScore() - 2);
            playerwithlongestroad.setScore(playerwithlongestroad.getScore() + 2);
            playerwithlongestroadIndex = players.indexOf(playerwithlongestroad);
        }
        if(max > 0) longestRoad = max;
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

    public Road addRoad(double startX1, double startY1, double endX1, double endY1, Hexagon hexagon){
        for(Road other : roads){
            if(other.intersects(startX1, startY1, endX1, endY1)){
                hexagon.addRoad(other);
                return null;
            }
        }
        Road road = new Road(startX1, startY1, endX1, endY1);
        hexagon.addRoad(road);
        roads.add(road);
        return road;

    }

    public Settlement addSettlement(double centerX, double centerY, Hexagon hexagon){
        for(Settlement other : settlements){
            if(other.ontop(centerX, centerY)){
                hexagon.addSettlement(other);
                return null;
            }
        }
        Settlement settlement = new Settlement(centerX, centerY);
        settlements.add(settlement);
        hexagon.addSettlement(settlement);
        return settlement;
    }

    public boolean isSettlementPossible(Settlement settlement){
        List<Road> neighbourRoads = roads.stream().filter(
                road -> road.neighbour(settlement)
        ).toList();
        for(Road road : neighbourRoads){
            for(Settlement other : settlements){
                if(other.getOwner() == null) continue;
                if(road.neighbour(other)){
                    return false;
                }
            }
        }
        return true;
    }
}