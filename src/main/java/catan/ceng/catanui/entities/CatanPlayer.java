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

    public boolean buildSettlement() {
        return true;
        // Implement logic to build a settlement, deduct the required resources, and update the player's settlements count
        // Return true if successful, false if not enough resources
    }

    public boolean buildRoad() {
        return true;
        // Implement logic to build a road, deduct the required resources, and update the player's roads count
        // Return true if successful, false if not enough resources
    }

    public boolean buildCity() {
        return true;
        // Implement logic to upgrade a settlement to a city, deduct the required resources, and update the player's cities count
        // Return true if successful, false if not enough resources
    }
}