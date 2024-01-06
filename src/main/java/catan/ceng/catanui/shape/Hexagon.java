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

public class Hexagon extends StackPane {
    private static final double HEXAGON_RADIUS = 39.0;
    private String resource;
    private int number;
    private List<Road> roads;
    private List<Settlement> settlements;
    private static final Map<String, String> RESOURCE_IMAGES = createResourceImagesMap();

    public Hexagon(String resource, int number) {
        this.resource=resource;
        this.number=number;
        setAlignment(Pos.CENTER);
        getChildren().addAll(createHexagon(resource), createContent(number));
        roads= new ArrayList<>();
        settlements= new ArrayList<>();
        
    }

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

    public void addRoad(Road road){
        roads.add(road);
    }

    public void addSettlement(Settlement settlement){
        settlements.add(settlement);
    }

    public int getNumber() {
        return number;
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

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