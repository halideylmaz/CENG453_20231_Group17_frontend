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
import catan.ceng.catanui.entities.CatanPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settlement {
    private double centerX;
    private double centerY;
    private double radius;
    private Polygon settlement;
    private CatanPlayer owner;

    public Settlement(double centerX, double centerY) {
        this.radius = 15;
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

    public CatanPlayer getOwner(){
        return owner;
    }

    public Polygon getSettlement(){
        return settlement;
    }

    public double getCenterX(){
        return centerX;
    }

    public double getCenterY(){
        return centerY;
    }

    public boolean ontop(double centerX1, double centerY1){
        double distance = Math.sqrt(Math.pow(centerX1-centerX,2)+Math.pow(centerY1-centerY,2));
        if(distance<radius){
            return true;
        }
        return false;
    }

}