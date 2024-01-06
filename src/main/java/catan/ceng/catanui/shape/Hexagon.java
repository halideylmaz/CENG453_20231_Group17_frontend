package catan.ceng.catanui.shape;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import catan.ceng.catanui.enums.ResourceType;

/**
 * The {@code Hexagon} class represents a hexagonal tile on a Catan board.
 * It extends JavaFX's StackPane and includes information about its resource type, number, and associated roads and settlements.
 * The hexagon contains a graphical representation of its resource type and number.
 *
 * @author Your Name
 * @version 1.0
 */
public class Hexagon extends StackPane {
    private static final double HEXAGON_RADIUS = 39.0;
    private String resource;
    private int number;
    private List<Road> roads;
    private List<Settlement> settlements;
    private static final Map<String, String> RESOURCE_IMAGES = createResourceImagesMap();

    /**
     * Constructs a new hexagon with the specified resource type and number.
     *
     * @param resource The resource type of the hexagon.
     * @param number   The number associated with the hexagon.
     */
    public Hexagon(String resource, int number) {
        this.resource=resource;
        this.number=number;
        setAlignment(Pos.CENTER);
        getChildren().addAll(createHexagon(resource), createContent(number));
        roads= new ArrayList<>();
        settlements= new ArrayList<>();
        
    }

    /**
     * Gets the resource type of the hexagon.
     *
     * @return The resource type of the hexagon.
     */
    public ResourceType getResourceType(){
        switch(resource){
            case "Hill":
                return ResourceType.BRICK;
            case "Mountain":
                return ResourceType.ORE;
            case "Forest":
                return ResourceType.LUMBER;
            case "Field":
                return ResourceType.GRAIN;
            case "Pasture":
                return ResourceType.WOOL;
            default:
                return null;
        }
    }

    /**
     * Adds a road to the list of roads associated with the hexagon.
     *
     * @param road The road to be added.
     */
    public void addRoad(Road road){
        roads.add(road);
    }

    /**
     * Adds a settlement to the list of settlements associated with the hexagon.
     *
     * @param settlement The settlement to be added.
     */
    public void addSettlement(Settlement settlement){
        settlements.add(settlement);
    }

    /**
     * Gets the number associated with the hexagon.
     *
     * @return The number associated with the hexagon.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the list of settlements associated with the hexagon.
     *
     * @return The list of settlements associated with the hexagon.
     */
    public List<Settlement> getSettlements() {
        return settlements;
    }

    // Private Methods

    /**
     * Creates a graphical representation of the hexagon using JavaFX Polygon with a specified resource type.
     *
     * @param resource The resource type of the hexagon.
     * @return The hexagon as a JavaFX Polygon.
     */
    private Polygon createHexagon(String resource) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI / 6 * i;
            double y = HEXAGON_RADIUS * Math.cos(angle);
            double x = HEXAGON_RADIUS * Math.sin(angle);

            hexagon.getPoints().addAll(x, y);


        }
        String imageUrl = RESOURCE_IMAGES.get(resource);
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        hexagon.setFill(new ImagePattern(image));
        return hexagon;
    }

    /**
     * Creates the content of the hexagon, including the graphical representation of its number.
     *
     * @param number The number associated with the hexagon.
     * @return The StackPane representing the content of the hexagon.
     */
    private StackPane createContent( int number) {
        StackPane content = new StackPane();


        if (number != -1) { // Ignore the number for the desert
            Text numberText = new Text(String.valueOf(number));
            numberText.setFill(Color.BLACK);
            numberText.setFont(new Font("Arial",20));
            content.getChildren().add(numberText);
        }

        return content;
    }

    /**
     * Creates a mapping of resource types to their corresponding image URLs.
     *
     * @return The resource type to image URL mapping.
     */
    private static Map<String, String> createResourceImagesMap() {

        Map<String, String> resourceImages = new HashMap<>();
        resourceImages.put("Hill", "/images/hill.png");
        resourceImages.put("Mountain", "/images/mountain.png");
        resourceImages.put("Forest", "/images/forest.png");
        resourceImages.put("Field", "/images/field.png");
        resourceImages.put("Pasture", "/images/pasture.png");
        resourceImages.put("Desert","/images/desert.png");

        return resourceImages;
    }

}