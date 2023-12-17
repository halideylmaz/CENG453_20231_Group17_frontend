package catan.ceng.catanui.entities;

import catan.ceng.catanui.enums.ResourceType;
import java.util.HashMap;
import java.util.Map;

public class CatanPlayer {
    private String playerName;
    private boolean isAI;
    private String color;
    private Long score;
    private int roads;
    private int settlements;
    private int cities;
    private Map<ResourceType, Integer> resources; // Store the player's resource cards

    public CatanPlayer(String playerName, boolean isAI, String color) {
        this.playerName = playerName;
        this.isAI = isAI;
        this.color = color;
        this.score = 0L;
        this.roads = 0;
        this.settlements = 0;
        this.cities = 0;
        this.resources = initializeResourceMap();
    }

    public Long getScore() {
        return score;
    }

    public String getColor() {
        return color;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // Helper method to initialize the resource card map with zeros
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

    // Methods to perform actions during a player's turn (e.g., collect resources, build settlements, etc.)

    public void addResource(ResourceType resourceType, int amount) {
        // Add a specified amount of a resource to the player's inventory
        int currentAmount = resources.get(resourceType);
        resources.put(resourceType, currentAmount + amount);
    }

    public void removeResource(ResourceType resourceType, int amount) {
        // Remove a specified amount of a resource from the player's inventory
        int currentAmount = resources.get(resourceType);
        resources.put(resourceType, currentAmount - amount);
    }

    public int getResourceCount(ResourceType resourceType) {
        // Return the number of cards of a specified resource type the player has
        return resources.get(resourceType);
    }

    public void automateTurn() {
        // Implement logic to automate a player's turn
        // This method will be called when the player is an AI
        // You can use the methods you implemented below to build settlements, roads, and cities
        simulateDelay();
        if(getResourceCount(ResourceType.LUMBER) >= 1 && getResourceCount(ResourceType.BRICK) >= 1) {
            buildRoad();
        }

        simulateDelay();
        if(getResourceCount(ResourceType.LUMBER) >= 1 && getResourceCount(ResourceType.BRICK) >= 1 && getResourceCount(ResourceType.WOOL) >= 1 && getResourceCount(ResourceType.GRAIN) >= 1) {
            buildSettlement();
        }

        simulateDelay();
        if(getResourceCount(ResourceType.GRAIN) >= 2 && getResourceCount(ResourceType.ORE) >= 3) {
            buildCity();
        }

    }

    private void simulateDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle InterruptedException, if needed
            System.out.println("Interrupted");
        }
    }

    public boolean buildSettlement() {
        if(getResourceCount(ResourceType.LUMBER) < 1 || getResourceCount(ResourceType.BRICK) < 1 || getResourceCount(ResourceType.WOOL) < 1 || getResourceCount(ResourceType.GRAIN) < 1) {
            return false;
        }
        //if settlement is can be built according to rules about roads and settlements
        if(true){
            removeResource(ResourceType.LUMBER, 1);
            removeResource(ResourceType.BRICK, 1);
            removeResource(ResourceType.WOOL, 1);
            removeResource(ResourceType.GRAIN, 1);
            setSettlements(getSettlements() + 1);
            //add settlement to player's settlements
            setScore(getScore() + 1);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean buildRoad() {

        if(getResourceCount(ResourceType.LUMBER) < 1 || getResourceCount(ResourceType.BRICK) < 1) {
            return false;
        }
        //if road is can be built according to rules about roads and settlements
        if(true){
            removeResource(ResourceType.LUMBER, 1);
            removeResource(ResourceType.BRICK, 1);
            setRoads(getRoads() + 1);
            //add road to player's roads
            //if longest road is achieved update score and longest road owner
            return true;
        }
        else{
            return false;
        }
    }

    public boolean buildCity() {
        if(getResourceCount(ResourceType.GRAIN) < 2 || getResourceCount(ResourceType.ORE) < 3) {
            return false;
        }
        //if city is can be built according to rules about cities and settlements
        if(true){
            removeResource(ResourceType.GRAIN, 2);
            removeResource(ResourceType.ORE, 3);
            setCities(getCities() + 1);
            setSettlements(getSettlements() - 1);
            //add city to player's cities
            setScore(getScore() + 2);
            return true;
        }
        else{
            return false;
        }
    }
}