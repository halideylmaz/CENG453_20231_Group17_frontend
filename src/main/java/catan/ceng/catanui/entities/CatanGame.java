package catan.ceng.catanui.entities;
import catan.ceng.catanui.shape.Hexagon;
import java.util.ArrayList;
import java.util.List;
import catan.ceng.catanui.entities.CatanPlayer;
import catan.ceng.catanui.controller.CatanController;
import catan.ceng.catanui.enums.ResourceType;
import catan.ceng.catanui.shape.Road;
import catan.ceng.catanui.shape.Settlement;

/**
 * The {@code CatanGame} class represents a game of Catan. It manages the game state, including the players,
 * hexagons, roads, settlements, and provides methods to perform game-related actions such as adding hexagons,
 * updating the longest road, ending turns, and checking for game over conditions.
 *
 */
public class CatanGame {
    private static final int BOARD_SIZE = 5;
    private List<CatanPlayer> players;
    private int currentPlayerIndex;
    private int playerwithlongestroadIndex = -1;
    private List<Hexagon> hexagons = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();
    private List<Settlement> settlements = new ArrayList<>();
    public int longestRoad;


    /**
     * Constructs a new Catan game with the given list of players.
     *
     * @param players The list of players participating in the game.
     */
    public CatanGame(List<CatanPlayer> players) {
        this.players = players;
        currentPlayerIndex = 0; // Set the starting player index
        longestRoad = 5;
    }

    /**
     * Adds a hexagon to the game's list of hexagons.
     *
     * @param hexagon The hexagon to be added.
     */
    public void addHexagon(Hexagon hexagon){
        hexagons.add(hexagon);
    }

    /**
     * Sets the list of players for the game.
     *
     * @param players The list of players to set.
     */
    public void setPlayers(List<CatanPlayer> players){
        this.players=players;
    }

    /**
     * Gets the list of players in the game.
     *
     * @return The list of players.
     */
    public List<CatanPlayer> getPlayers(){
        return players;
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return The current player.
     */
    public CatanPlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Gets the player with the highest score in the game.
     *
     * @return The player with the highest score.
     */
    public CatanPlayer getPlayerwithHighestScore() {
        CatanPlayer playerwithHighestScore = players.get(0);
        for (CatanPlayer player : players) {
            if (player.getScore() > playerwithHighestScore.getScore()) {
                playerwithHighestScore = player;
            }
        }
        return playerwithHighestScore;
    }

    /**
     * Gets the player with the longest road in the game.
     *
     * @return The player with the longest road, or {@code null} if no player has the longest road.
     */
    public CatanPlayer getPlayerwithLongestRoad() {
        if(playerwithlongestroadIndex == -1){
            return null;
        }
        else return players.get(playerwithlongestroadIndex);
    }

    /**
     * Gets the list of settlements in the game.
     *
     * @return The list of settlements.
     */
    public List<Settlement> getSettlements() {
        return settlements;
    }

    /**
     * Gets the list of roads in the game.
     *
     * @return The list of roads.
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     * Updates the longest road in the game based on player road lengths.
     */
    public void updateLongestRoad(){
        int max = 0;
        CatanPlayer playerwithlongestroad = null;
        for(CatanPlayer player : players){
            if(player.getLongestRoad() > max && player.getLongestRoad() >= longestRoad){
                max = player.getLongestRoad();
                playerwithlongestroad = player;
            }
        }
        if(playerwithlongestroad != null){
            if(playerwithlongestroadIndex != -1 ) getPlayerwithLongestRoad().setScore(getPlayerwithLongestRoad().getScore() - 2);
            playerwithlongestroad.setScore(playerwithlongestroad.getScore() + 2);
            playerwithlongestroadIndex = players.indexOf(playerwithlongestroad);
        }
        if(max > 0) longestRoad = max;
    }

    /**
     * Gets the length of the longest road in the game.
     *
     * @return The length of the longest road.
     */
    public int getLongestRoad() {
        return longestRoad;
    }

    /**
     * Gets a player by their username.
     *
     * @param username The username of the player to retrieve.
     * @return The player with the specified username, or {@code null} if not found.
     */
    public CatanPlayer getPlayerbyUsername(String username){
        for(CatanPlayer player:players){
            if(player.getPlayerName().equals(username)){
                return player;
            }
        }
        return null;
    }

    /**
     * Ends the turn of the current player, moving to the next player's turn.
     */
    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player's turn
    }

    /**
     * Checks if the game is over based on player scores.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        for (CatanPlayer player : players) {
            if (player.getScore() >= 8) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a road to the game, associating it with a hexagon.
     *
     * @param startX1 The x-coordinate of the starting point of the road.
     * @param startY1 The y-coordinate of the starting point of the road.
     * @param endX1   The x-coordinate of the ending point of the road.
     * @param endY1   The y-coordinate of the ending point of the road.
     * @param hexagon The hexagon associated with the road.
     * @return The added road, or {@code null} if the road intersects (already exists) with existing roads.
     */
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

    /**
     * Adds a settlement to the game, associating it with a hexagon.
     *
     * @param centerX The x-coordinate of the center of the settlement.
     * @param centerY The y-coordinate of the center of the settlement.
     * @param hexagon The hexagon associated with the settlement.
     * @return The added settlement, or {@code null} if the settlement overlaps with existing settlements.
     */
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

    /**
     * Checks if placing a settlement at a specific location is possible without violating game rules.
     *
     * @param settlement The settlement to be placed.
     * @return {@code true} if placing the settlement is possible, {@code false} otherwise.
     */
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

    /**
     * Distributes resources based on the sum of the dice roll to settlements on specific hexagons.
     *
     * @param sum The sum of the dice roll.
     */
    public void handleResources(int sum){
        for(Hexagon hexagon : hexagons){
            if(hexagon. getNumber() == sum){
                for(Settlement settlement : hexagon.getSettlements()){
                    if(settlement.getOwner() != null){
                        int amount = settlement.isCity() ? 2 : 1;
                        settlement.getOwner().addResource(hexagon.getResourceType(), amount);
                    }
                }
            }
        }
    }
}