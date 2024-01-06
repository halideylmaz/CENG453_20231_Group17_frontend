package catan.ceng.catanui.entities;

import catan.ceng.catanui.enums.ResourceType;
import catan.ceng.catanui.shape.Road;
import catan.ceng.catanui.shape.Settlement;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import catan.ceng.catanui.entities.GameConstants;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * The {@code CatanPlayer} class represents a player in the game of Catan.
 * It includes information about the player's name, AI status, color, score, resources, roads, settlements, cities,
 * and methods for building roads, settlements, and cities, as well as simulating an AI player's turn.
 *
 */
public class CatanPlayer {
    private String playerName;
    private boolean isAI;
    private String color;
    private Long score;
    private int roads;
    private int settlements;
    private int cities;
    private int longestRoad;
    private Map<ResourceType, Integer> resources; // Store the player's resource cards
    private List<Road> roadsList = new ArrayList<>();
    private List<Settlement> settlementsList = new ArrayList<>();

    /**
     * Constructs a new Catan player with the specified name, AI status, and color.
     *
     * @param playerName The name of the player.
     * @param isAI       {@code true} if the player is controlled by AI, {@code false} otherwise.
     * @param color      The color associated with the player.
     */
    public CatanPlayer(String playerName, boolean isAI, String color) {
        this.playerName = playerName;
        this.isAI = isAI;
        this.color = color;
        this.score = 0L;
        this.roads = 0;
        this.settlements = 0;
        this.cities = 0;
        this.longestRoad = 0;
        this.resources = initializeResourceMap();
    }

    /**
     * Initializes the player's initial resources at the beginning of the game.
     */
    public void initialResources(){
        this.addResource(ResourceType.LUMBER, 3);
        this.addResource(ResourceType.BRICK, 3);
        this.addResource(ResourceType.GRAIN, 1);
        this.addResource(ResourceType.WOOL, 1);
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's score.
     */
    public Long getScore() {
        return score;
    }

    /**
     * Gets the color associated with the player.
     *
     * @return The player's color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Checks if the player is controlled by AI.
     *
     * @return {@code true} if the player is controlled by AI, {@code false} otherwise.
     */
    public boolean isAI() {
        return isAI;
    }

    /**
     * Sets the player's score to the specified value.
     *
     * @param score The new score for the player.
     */
    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * Gets the number of roads owned by the player.
     *
     * @return The number of roads owned by the player.
     */
    public int getRoads() {
        return roads;
    }

    /**
     * Gets the length of the player's longest road.
     *
     * @return The length of the player's longest road.
     */
    public int getLongestRoad() {
        return longestRoad;
    }

    /**
     * Computes the length of the player's longest road using depth-first search (DFS).
     */
    private void computeLongestRoad(){
        int maxConsecutiveRoads = 0;
        for (Road startingRoad : roadsList) {
            int consecutiveRoads = dfsCountConsecutiveRoads(startingRoad, new ArrayList<>());
            maxConsecutiveRoads = Math.max(maxConsecutiveRoads, consecutiveRoads);
        }
        longestRoad = maxConsecutiveRoads;
        System.out.println("Longest road for player " + playerName + " is " + longestRoad);
    }

    /**
     * Performs depth-first search (DFS) to count consecutive roads starting from a given road.
     *
     * @param currentRoad   The current road being examined.
     * @param visitedRoads  The list of visited roads during the DFS.
     * @return The maximum consecutive roads count starting from the current road.
     */
    private int dfsCountConsecutiveRoads(Road currentRoad, List<Road> visitedRoads) {
        visitedRoads.add(currentRoad);
        int maxConsecutive = 1; // Initialize with 1 for the current road
        List<Road> neighbors = roadsList.stream()
                .filter(road -> road.neighbour(currentRoad))
                .collect(Collectors.toList());

        for (Road neighborRoad : neighbors) {
            if (!visitedRoads.contains(neighborRoad)) {
                int consecutive = 1 + dfsCountConsecutiveRoads(neighborRoad, visitedRoads);
                maxConsecutive = Math.max(maxConsecutive, consecutive);
            }
        }
        // Backtrack: Remove the current road from the visited list
        visitedRoads.remove(currentRoad);
        return maxConsecutive;
    }

    /**
     * Sets the number of roads built by the player.
     *
     * @param roads The new number of roads for the player.
     */
    public void setRoads(int roads) {
        this.roads = roads;
    }

    /**
     * Gets the number of settlements built by the player.
     *
     * @return The number of settlements built by the player.
     */
    public int getSettlements() {
        return settlements;
    }

    /**
     * Sets the number of settlements built by the player.
     *
     * @param settlements The new number of settlements for the player.
     */
    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    /**
     * Gets the number of cities built by the player.
     *
     * @return The number of cities built by the player.
     */
    public int getCities() {
        return cities;
    }

    /**
     * Sets the number of cities built by the player.
     *
     * @param cities The new number of cities for the player.
     */
    public void setCities(int cities) {
        this.cities = cities;
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player's name to the specified value.
     *
     * @param playerName The new name for the player.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Helper method to initialize the resource card map with zeros for all resource types.
     * The resource types are assumed to be represented by an Enum (e.g., wood, brick, ore, grain, wool).
     *
     * @return A map with all resource types initialized to zero cards.
     */
    private Map<ResourceType, Integer> initializeResourceMap() {
        // Initialize the map with all resource types having zero cards initially
        // You can use an Enum to represent resource types (e.g., wood, brick, ore, grain, wool)
        // This depends on how you define ResourceType in your code
        // Initialize the map with all resource types having zero cards initially
        Map<ResourceType, Integer> resourceMap = new HashMap<>();
        for (ResourceType resourceType : ResourceType.values()) {
            resourceMap.put(resourceType, 0);
        }
        return resourceMap;
    }

    /**
     * Gets the list of settlements owned by the player.
     *
     * @return The list of settlements built by the player.
     */
    public List<Settlement> getSettlementsList() {
        return settlementsList;
    }

    /**
     * Adds a specified amount of a resource to the player's inventory.
     *
     * @param resourceType The type of resource to be added.
     * @param amount       The amount of the resource to be added.
     */
    public void addResource(ResourceType resourceType, int amount) {
        // Add a specified amount of a resource to the player's inventory
        int currentAmount = resources.get(resourceType);
        resources.put(resourceType, currentAmount + amount);
    }

    /**
     * Removes a specified amount of a resource from the player's inventory.
     *
     * @param resourceType The type of resource to be removed.
     * @param amount       The amount of the resource to be removed.
     */
    public void removeResource(ResourceType resourceType, int amount) {
        // Remove a specified amount of a resource from the player's inventory
        int currentAmount = resources.get(resourceType);
        resources.put(resourceType, currentAmount - amount);
    }

    /**
     * Gets the count of a specified resource type in the player's inventory.
     *
     * @param resourceType The type of resource to be counted.
     * @return The count of the specified resource type.
     */
    public int getResourceCount(ResourceType resourceType) {
        // Return the number of cards of a specified resource type the player has
        return resources.get(resourceType);
    }

    /**
     * Simulates an automated turn for an AI player, including building roads, settlements, and cities.
     */
    public void automateTurn() {
        simulateDelay();
        if(getResourceCount(ResourceType.LUMBER) >= 1 && getResourceCount(ResourceType.BRICK) >= 1) {
            List<Road> neighborRoads = new ArrayList<>();
            List<Road> allRoads = GameConstants.game.getRoads();
            for(Road otherroad: allRoads){
                for(Road road: roadsList){
                    if(road.neighbour(otherroad)){
                        neighborRoads.add(otherroad);
                    }
                }
            }
            Collections.shuffle(neighborRoads);
            for(Road road: neighborRoads){
                if(buildRoad(road)){
                    break;
                }
            }
        }

        simulateDelay();
        if(getResourceCount(ResourceType.LUMBER) >= 1 && getResourceCount(ResourceType.BRICK) >= 1 && getResourceCount(ResourceType.WOOL) >= 1 && getResourceCount(ResourceType.GRAIN) >= 1) {
            List<Settlement> ownerlessSettlements = GameConstants.game.getSettlements().stream()
                    .filter(settlement -> settlement.getOwner() == null).collect(Collectors.toList());
            Collections.shuffle(ownerlessSettlements);
            for(Settlement settlement: ownerlessSettlements){
                if(buildSettlement(settlement)){
                    break;
                }
            }
        }

        simulateDelay();
        if(getResourceCount(ResourceType.GRAIN) >= 2 && getResourceCount(ResourceType.ORE) >= 3) {
            List<Settlement> settlement = settlementsList.stream()
                    .filter(settlement1 -> !settlement1.isCity()).collect(Collectors.toList());
            Collections.shuffle(settlement);
            for(Settlement settlement1: settlement){
                if(buildCity(settlement1)){
                    break;
                }
            }
        }

    }

    /**
     * Simulates a delay in AI turns.
     */
    private void simulateDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle InterruptedException, if needed
            System.out.println("Interrupted");
        }
    }

    /**
     * Builds a settlement for the player.
     *
     * @param settlement The settlement to be built.
     * @return {@code true} if the settlement is successfully built, {@code false} otherwise.
     */
    public boolean buildSettlement(Settlement settlement) {
        if(getResourceCount(ResourceType.LUMBER) < 1 || getResourceCount(ResourceType.BRICK) < 1 || getResourceCount(ResourceType.WOOL) < 1 || getResourceCount(ResourceType.GRAIN) < 1) {
            return false;
        }
        //if settlement is can be built according to rules about roads and settlements
        if(settlement.getOwner() != null){
            return false;
        }

        if(GameConstants.game.isSettlementPossible(settlement)){
            removeResource(ResourceType.LUMBER, 1);
            removeResource(ResourceType.BRICK, 1);
            removeResource(ResourceType.WOOL, 1);
            removeResource(ResourceType.GRAIN, 1);
            //add settlement to player's settlements
            addSettlement(settlement);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Adds a settlement to the player's list of settlements, sets the player as the owner,
     * increments the settlements count, and updates the player's score.
     *
     * @param settlement The settlement to be added.
     */
    public void addSettlement(Settlement settlement){
        settlementsList.add(settlement);
        settlement.setOwner(this);
        settlements += 1;
        setScore(getScore() + 1);
    }

    /**
     * Adds a road to the player's list of roads, sets the player as the owner,
     * increments the roads count, and recomputes the player's longest road.
     *
     * @param road The road to be added.
     */
    public void addRoad(Road road){
        roadsList.add(road);
        roads += 1;
        road.setOwner(this);
        computeLongestRoad();
    }

    /**
     * Builds a road for the player.
     *
     * @param road The road to be built.
     * @return {@code true} if the road is successfully built, {@code false} otherwise.
     */
    public boolean buildRoad(Road road){

        if(getResourceCount(ResourceType.LUMBER) < 1 || getResourceCount(ResourceType.BRICK) < 1) {
            return false;
        }
        if(road.getOwner() != null){
            return false;
        }
        //if road is can be built according to rules about roads and settlements
        boolean canBuild = false;
        for(Road r : roadsList){
            if(r.neighbour(road)){
                canBuild = true;
                break;
            }
        }

        if(!canBuild){
            for(Settlement s : settlementsList){
                if(road.neighbour(s)){
                    canBuild = true;
                    break;
                }
            }
        }

        if(canBuild){
            removeResource(ResourceType.LUMBER, 1);
            removeResource(ResourceType.BRICK, 1);
            addRoad(road);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Builds a city for the player.
     *
     * @param settlement The settlement to be upgraded to a city.
     * @return {@code true} if the city is successfully built, {@code false} otherwise.
     */
    public boolean buildCity(Settlement settlement) {
        if(getResourceCount(ResourceType.GRAIN) < 2 || getResourceCount(ResourceType.ORE) < 3) {
            return false;
        }
        //if city is can be built according to rules about cities and settlements
        if(settlement.getOwner() != this){
            return false;
        }
        if(true){
            removeResource(ResourceType.GRAIN, 2);
            removeResource(ResourceType.ORE, 3);
            settlement.toCity();
            settlements -= 1;
            cities += 1;
            //add city to player's cities
            //remove settlement score add city score
            setScore(getScore() + 1);
            return true;
        }
        else{
            return false;
        }
    }
}