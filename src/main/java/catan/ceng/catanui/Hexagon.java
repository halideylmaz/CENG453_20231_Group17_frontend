package catan.ceng.catanui;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.tree.VariableHeightLayoutCache;
import java.util.HashMap;
import java.util.Map;

public class Hexagon extends StackPane {
    private static final double HEXAGON_RADIUS = 39.0;
    private static final Map<String, String> RESOURCE_IMAGES = createResourceImagesMap();

    public Hexagon(String resource, int number) {
        setAlignment(Pos.CENTER);
        getChildren().addAll(createHexagon(), createContent(resource, number));
    }

    private Polygon createHexagon() {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI / 6 * i;
            double y = HEXAGON_RADIUS * Math.cos(angle);
            double x = HEXAGON_RADIUS * Math.sin(angle);
            hexagon.setFill(Color.LIGHTGRAY);
            hexagon.getPoints().addAll(x, y);

        }
        return hexagon;
    }

    private StackPane createContent(String resource, int number) {
        StackPane content = new StackPane();

        if (RESOURCE_IMAGES.containsKey(resource)) {
            String imageUrl = RESOURCE_IMAGES.get(resource);
            Image image = new Image(getClass().getResourceAsStream(imageUrl));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(HEXAGON_RADIUS * 1.7);
            imageView.setFitHeight(HEXAGON_RADIUS *2);
            content.getChildren().add(imageView);
        } else {

            Text text = new Text(resource);
            content.getChildren().add(text);
        }

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