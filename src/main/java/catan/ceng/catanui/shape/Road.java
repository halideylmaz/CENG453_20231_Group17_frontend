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

public class Road{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Line line;
    private CatanPlayer owner;
    private int strokeWidth=10;

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

    public boolean intersects(double startX1, double startY1, double endX1, double endY1) {
        if(Math.abs(startX1-startX) <= strokeWidth && Math.abs(startY1-startY) <= strokeWidth && Math.abs(endX1-endX) <= strokeWidth && Math.abs(endY1-endY) <= strokeWidth){
            return true;
        }
        if(Math.abs(startX1-endX) <= strokeWidth && Math.abs(startY1-endY) <= strokeWidth && Math.abs(endX1-startX) <= strokeWidth && Math.abs(endY1-startY) <= strokeWidth){
            return true;
        }
        else return false;
    }

    public boolean neighbour(Settlement settlement){
        if(Math.abs(startX-settlement.getCenterX()) <= strokeWidth && Math.abs(startY-settlement.getCenterY()) <= strokeWidth){
            return true;
        }
        else if(Math.abs(endX-settlement.getCenterX()) <= strokeWidth && Math.abs(endY-settlement.getCenterY()) <= strokeWidth){
            return true;
        }
        else return false;
    }

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

    public Line getLine(){
        return line;
    }

    public CatanPlayer getOwner(){
        return owner;
    }

    public void setOwner(CatanPlayer owner){
        this.owner=owner;
        if(owner !=null ){
            Color lineColor = Color.web(owner.getColor());
            line.setStroke(lineColor);
        }
        else line.setStroke(Color.WHITE);
    }

    public double getStartX(){
        return startX;
    }

    public double getStartY(){
        return startY;
    }

    public double getEndX(){
        return endX;
    }

    public double getEndY(){
        return endY;
    }

}