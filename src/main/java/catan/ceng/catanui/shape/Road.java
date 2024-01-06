package catan.ceng.catanui.shape;

import catan.ceng.catanui.shape.Settlement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import catan.ceng.catanui.entities.CatanPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The {@code Road} class represents a road on a Catan board, connecting two points.
 * It contains information about its starting and ending coordinates, owner, and graphical representation.
 *
 */
public class Road{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Line line;
    private CatanPlayer owner;
    private int strokeWidth=10;

    /**
     * Constructs a new road with the specified starting and ending coordinates.
     *
     * @param startX The x-coordinate of the starting point.
     * @param startY The y-coordinate of the starting point.
     * @param endX   The x-coordinate of the ending point.
     * @param endY   The y-coordinate of the ending point.
     */
    public Road(double startX, double startY, double endX, double endY){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.owner=null;
        line= new Line(startX,startY,endX,endY);
        line.setStrokeWidth(strokeWidth);
        line.setStroke(Color.WHITE);
    }

    /**
     * Checks if the road intersects (is same) with another road defined by its starting and ending coordinates.
     *
     * @param startX1 The x-coordinate of the starting point of the other road.
     * @param startY1 The y-coordinate of the starting point of the other road.
     * @param endX1   The x-coordinate of the ending point of the other road.
     * @param endY1   The y-coordinate of the ending point of the other road.
     * @return {@code true} if the roads intersect, {@code false} otherwise.
     */
    public boolean intersects(double startX1, double startY1, double endX1, double endY1) {
        if(Math.abs(startX1-startX) <= strokeWidth && Math.abs(startY1-startY) <= strokeWidth && Math.abs(endX1-endX) <= strokeWidth && Math.abs(endY1-endY) <= strokeWidth){
            return true;
        }
        if(Math.abs(startX1-endX) <= strokeWidth && Math.abs(startY1-endY) <= strokeWidth && Math.abs(endX1-startX) <= strokeWidth && Math.abs(endY1-startY) <= strokeWidth){
            return true;
        }
        else return false;
    }

    /**
     * Checks if the road is a neighbor to a settlement.
     *
     * @param settlement The settlement to check for adjacency.
     * @return {@code true} if the road is adjacent to the settlement, {@code false} otherwise.
     */
    public boolean neighbour(Settlement settlement){
        if(Math.abs(startX-settlement.getCenterX()) <= strokeWidth && Math.abs(startY-settlement.getCenterY()) <= strokeWidth){
            return true;
        }
        else if(Math.abs(endX-settlement.getCenterX()) <= strokeWidth && Math.abs(endY-settlement.getCenterY()) <= strokeWidth){
            return true;
        }
        else return false;
    }

    /**
     * Checks if the road is a neighbor to another road.
     *
     * @param other The other road to check for adjacency.
     * @return {@code true} if the roads are adjacent, {@code false} otherwise.
     */
    public boolean neighbour(Road other){
        if(Math.abs(startX-other.startX) <= strokeWidth && Math.abs(startY-other.startY) <= strokeWidth){
            return true;
        }
        else if(Math.abs(startX-other.endX) <= strokeWidth && Math.abs(startY-other.endY) <= strokeWidth){
            return true;
        }
        else if(Math.abs(endX-other.startX) <= strokeWidth && Math.abs(endY-other.startY) <= strokeWidth){
            return true;
        }
        else if(Math.abs(endX-other.endX) <= strokeWidth && Math.abs(endY-other.endY) <= strokeWidth){
            return true;
        }
        else return false;
    }

    /**
     * Gets the graphical representation of the road as a JavaFX Line object.
     *
     * @return The Line object representing the road.
     */
    public Line getLine(){
        return line;
    }

    /**
     * Gets the owner of the road.
     *
     * @return The player who owns the road.
     */
    public CatanPlayer getOwner(){
        return owner;
    }

    /**
     * Sets the owner of the road and updates its appearance (color) accordingly.
     *
     * @param owner The player who owns the road.
     */
    public void setOwner(CatanPlayer owner){
        this.owner=owner;
        if(owner !=null ){
            Color lineColor = Color.web(owner.getColor());
            line.setStroke(lineColor);
        }
        else line.setStroke(Color.WHITE);
    }

    /**
     * Gets the x-coordinate of the starting point of the road.
     *
     * @return The x-coordinate of the starting point.
     */
    public double getStartX(){
        return startX;
    }

    /**
     * Gets the y-coordinate of the starting point of the road.
     *
     * @return The y-coordinate of the starting point.
     */
    public double getStartY(){
        return startY;
    }

    /**
     * Gets the x-coordinate of the ending point of the road.
     *
     * @return The x-coordinate of the ending point.
     */
    public double getEndX(){
        return endX;
    }

    /**
     * Gets the y-coordinate of the ending point of the road.
     *
     * @return The y-coordinate of the ending point.
     */
    public double getEndY(){
        return endY;
    }

}