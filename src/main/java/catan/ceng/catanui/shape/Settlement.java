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
import javafx.scene.layout.Pane;
import catan.ceng.catanui.entities.CatanPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The {@code Settlement} class represents a settlement or city on a Catan board.
 * It contains information about its position, owner, and whether it is a city or not.
 * The settlement is initially created as a triangular structure and can be upgraded to a city as a square later.
 *
 *
 *
 */
public class Settlement {
    private double centerX;
    private double centerY;
    private double radius;
    private Polygon settlement;
    private Polygon city = null;
    private CatanPlayer owner;
    private boolean isCity;

    /**
     * Constructs a new settlement with the specified center coordinates.
     *
     * @param centerX The x-coordinate of the center.
     * @param centerY The y-coordinate of the center.
     */
    public Settlement(double centerX, double centerY) {
        this.radius = 15;
        isCity = false;
        Polygon triangle = new Polygon();
        for (int i = 0; i < 3; i++) {
            double angle = 2.0 * Math.PI / 3 * i;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            triangle.getPoints().addAll(x, y);
        }
        triangle.setFill(Color.WHITE);
        this.settlement = triangle;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Upgrades the settlement to a city by changing its structure to a square and increasing its radius.
     * The color of the city depends on its an owner.
     */
    public void toCity(){
        this.radius = 20;
        Polygon square = new Polygon();
        for (int i = 0; i < 4; i++) {
            double angle = 2.0 * Math.PI / 4 * i;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            square.getPoints().addAll(x, y);
        }
        if(owner !=null ){
            Color lineColor = Color.web(owner.getColor());
           square.setFill(lineColor);
        }
        else {
            square.setFill(Color.WHITE);
        }
        this.city = square;
        isCity = true;
    }

    /**
     * Checks if the settlement has been upgraded to a city.
     *
     * @return {@code true} if the settlement is a city, {@code false} otherwise.
     */
    public boolean isCity(){
        return isCity;
    }

    /**
     * Gets the polygon representation of the city structure.
     *
     * @return The city polygon, or {@code null} if it is not a city.
     */
    public Polygon getCity() {
        return city;
    }

    /**
     * Sets the owner of the settlement or city and updates its appearance (color) accordingly.
     *
     * @param owner The player who owns the settlement or city.
     */
    public void setOwner(CatanPlayer owner){
        this.owner=owner;
        if(owner !=null ){
            Color lineColor = Color.web(owner.getColor());
           settlement.setFill(lineColor);
        }
        else {
            settlement.setFill(Color.WHITE);
        }
    }

    /**
     * Gets the owner of the settlement or city.
     *
     * @return The player who owns the settlement or city.
     */
    public CatanPlayer getOwner(){
        return owner;
    }

    /**
     * Gets the polygon representation of the settlement.
     *
     * @return The settlement polygon.
     */
    public Polygon getSettlement(){
        return settlement;
    }

    /**
     * Gets the x-coordinate of the center of the settlement or city.
     *
     * @return The x-coordinate of the center.
     */
    public double getCenterX(){
        return centerX;
    }

    /**
     * Gets the y-coordinate of the center of the settlement or city.
     *
     * @return The y-coordinate of the center.
     */
    public double getCenterY(){
        return centerY;
    }

    /**
     * Checks if the settlement or city is on top of the specified coordinates.
     *
     * @param centerX1 The x-coordinate of the point to be checked.
     * @param centerY1 The y-coordinate of the point to be checked.
     * @return {@code true} if the settlement or city is on top of the specified coordinates, {@code false} otherwise.
     */
    public boolean ontop(double centerX1, double centerY1){
        double distance = Math.sqrt(Math.pow(centerX1-centerX,2)+Math.pow(centerY1-centerY,2));
        if(distance<radius){
            return true;
        }
        return false;
    }

}