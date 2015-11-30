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
    private float xLocation, yLocation, rotation;
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
        
        setPosition();
        
       // setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);
    }
    public abstract void act();
    
    protected void setPosition() {
        appearance.setTranslateX(getxLocation());
        appearance.setTranslateY(getyLocation());
        borders.setTranslateX(xLocation);
        borders.setTranslateY(yLocation);
        upBorder.setTranslateX(xLocation);
        upBorder.setTranslateY(yLocation);
        rightBorder.setTranslateX(xLocation);
        rightBorder.setTranslateY(yLocation);
        downBorder.setTranslateX(xLocation);
        downBorder.setTranslateY(yLocation);
        leftBorder.setTranslateX(xLocation);
        leftBorder.setTranslateY(yLocation);
    }
    protected void turnObject() {
        
    }

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
    }
    
    public int crashedInto(VisibleObject crasher) {
        if(crasher.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(crasher)) { 
        //    if(!this.equals(crasher)) { 

            if(crasher.getBorders().getBoundsInParent().intersects(upBorder.getBoundsInParent())) {
                return CRASH_UP;
            }
            
            if(crasher.getBorders().getBoundsInParent().intersects(rightBorder.getBoundsInParent())) {
                return CRASH_RIGHT;
            }
           
            if(crasher.getBorders().getBoundsInParent().intersects(downBorder.getBoundsInParent())) {
                return CRASH_DOWN;
            }
           
            if(crasher.getBorders().getBoundsInParent().intersects(leftBorder.getBoundsInParent())) {
                return CRASH_LEFT;
            }
        
        }
        return -1;
    }
    public boolean mayTurn(float degrees) {
        borders.setRotate(degrees);
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(object.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(object)) {
                borders.setRotate(rotation);
                return false;
            }
        }
        borders.setRotate(rotation);
        return true;
    }
    protected void setRotation(float degrees) {
        if(degrees < 0) {
            degrees = 360 + degrees;
            setRotation(degrees);
        }
        if(degrees > 360) {
            degrees = degrees - 360;
            setRotation(degrees);
        }
        rotation = degrees;
        appearance.setRotate(rotation);
        borders.setRotate(rotation);
        upBorder.setRotate(rotation);
        rightBorder.setRotate(rotation);
        downBorder.setRotate(rotation);
        leftBorder.setRotate(rotation);
    }
    protected float getFacingRotation() {
        return rotation;
    }
    public float getBounciness() {
        return details.getBounciness();
    }
}