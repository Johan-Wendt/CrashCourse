/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author johanwendt
 */
public abstract class VisibleObject {
    private VisibleObjects details;
    private float xLocation, yLocation;
    private ImageView appearance;
    private SVGPath borders, upBorder, rightBorder, downBorder, leftBorder;
    public static int CRASH_UP = 0;
    public static int CRASH_RIGHT = 1;
    public static int CRASH_DOWN = 2;
    public static int CRASH_LEFT = 3;
    
    
    
    public VisibleObject(CrashCourse crashCourse, VisibleObjects deatils) {
        this.details = deatils;
        appearance = new ImageView(details.getImages().get(0));
        crashCourse.getRoot().getChildren().add(appearance);
        
        setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);
    }
    
    public VisibleObject(CrashCourse crashCourse, VisibleObjects deatils, float xLocation, float yLocation) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        crashCourse.getRoot().getChildren().add(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);
    }
    public abstract void act();

    public float getxLocation() {
        return xLocation;
    }

    public void setxLocation(float xLocation) {
        this.xLocation = xLocation;
    }

    public float getyLocation() {
        return yLocation;
    }

    public void setyLocation(float yLocation) {
        this.yLocation = yLocation;
    }
    public ImageView getAppearance() {
        return appearance;
    }

    public SVGPath getBorders() {
        return borders;
    }
    public SVGPath getUpBorders() {
        return upBorder;
    }
    public SVGPath getRightBorders() {
        return rightBorder;
    }
    public SVGPath getDownBorders() {
        return downBorder;
    }
    public SVGPath getLeftBorders() {
        return leftBorder;
    }
    

    private void setBorders(CrashCourse crashCourse) {
        borders = new SVGPath();
        borders.setContent(details.getSVGData());
        upBorder = new SVGPath();
        upBorder.setContent(details.getSVGDataUpp());
        rightBorder = new SVGPath();
        rightBorder.setContent(details.getSVGDataRight());
        downBorder = new SVGPath();
        downBorder.setContent(details.getSVGDataDown());
        leftBorder = new SVGPath();
        leftBorder.setContent(details.getSVGDataLeft());
        
        crashCourse.getRoot().getChildren().addAll(borders, upBorder, rightBorder, downBorder, leftBorder);
        
        upBorder.setFill(Color.RED);
        rightBorder.setFill(Color.BLUE);
        downBorder.setFill(Color.GREEN);
        leftBorder.setFill(Color.YELLOW);
    }
    
    public int crashedInto(VisibleObject crasher) {
       // if(crasher.getAppearance().getBoundsInParent().intersects(getAppearance().getBoundsInParent()) && !this.equals(crasher)) { 
        
        Shape intersection = SVGPath.intersect(crasher.getBorders(), getUpBorders());
            if(SVGPath.intersect(crasher.getBorders(), getUpBorders()).getBoundsInLocal().getWidth() != -1) {
                return CRASH_UP;
            }
            
            if(SVGPath.intersect(crasher.getBorders(), getRightBorders()).getBoundsInLocal().getWidth() != -1) {
                return CRASH_RIGHT;
            }
           
            if(SVGPath.intersect(crasher.getBorders(), getDownBorders()).getBoundsInLocal().getWidth() != -1) {
                return CRASH_DOWN;
            }
           
            if(SVGPath.intersect(crasher.getBorders(), getLeftBorders()).getBoundsInLocal().getWidth() != -1) {
                return CRASH_LEFT;
            }
            /**
            if(crasher.getBorders().intersects(getUpBorders().getBoundsInLocal())) {
                return CRASH_UP;
            }
            
            if(crasher.getBorders().intersects(getRightBorders().getBoundsInLocal())) {
                return CRASH_RIGHT;
            }
           
            if(crasher.getBorders().intersects(getDownBorders().getBoundsInLocal())) {
                return CRASH_DOWN;
            }
           
            if(crasher.getBorders().intersects(getLeftBorders().getBoundsInLocal())) {
                return CRASH_LEFT;
            }
            * */
        
        
     //   }
        return -1;
    }
}
