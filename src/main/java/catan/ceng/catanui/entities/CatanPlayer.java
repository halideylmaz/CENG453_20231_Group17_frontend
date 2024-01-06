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

    public void initialResources(){
        this.addResource(ResourceType.LUMBER, 3);
        this.addResource(ResourceType.BRICK, 3);
        this.addResource(ResourceType.GRAIN, 1);
        this.addResource(ResourceType.WOOL, 1);
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

    public int getLongestRoad() {
        return longestRoad;
    }

    private void computeLongestRoad(){
        int maxConsecutiveRoads = 0;
        for (Road startingRoad : roadsList) {
            int consecutiveRoads = dfsCountConsecutiveRoads(startingRoad, new ArrayList<>());
            maxConsecutiveRoads = Math.max(maxConsecutiveRoads, consecutiveRoads);
        }
        longestRoad = maxConsecutiveRoads;
        System.out.println("Longest road for player " + playerName + " is " + longestRoad);
    }

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

    public List<Settlement> getSettlementsList() {
        return settlementsList;
    }

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

    private void simulateDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Handle InterruptedException, if needed
            System.out.println("Interrupted");
        }
    }

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
            settlements += 1;
            settlement.setOwner(this);
            settlementsList.add(settlement);
            setScore(getScore() + 1);
            return true;
        }
        else{
            return false;
        }
    }

    public void addSettlement(Settlement settlement){
        settlementsList.add(settlement);
        settlement.setOwner(this);
        settlements += 1;
        setScore(getScore() + 1);
    }

    public void addRoad(Road road){
        roadsList.add(road);
        roads += 1;
        road.setOwner(this);
        computeLongestRoad();
    }

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
            roadsList.add(road);
            road.setOwner(this);
            removeResource(ResourceType.LUMBER, 1);
            removeResource(ResourceType.BRICK, 1);
            setRoads(getRoads() + 1);
            computeLongestRoad();
            return true;
        }
        else{
            return false;
        }
    }

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