/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
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
    private double xLocation, yLocation, rotation;
    private ImageView appearance;
    private SVGPath borders, upBorder, rightBorder, downBorder, leftBorder;
    public static int CRASH_UP = 0;
    public static int CRASH_RIGHT = 1;
    public static int CRASH_DOWN = 2;
    public static int CRASH_LEFT = 3;
    public static int CRASH_DAMAGING = 4;
    public static int CRASH_HARMLESS = 5;
    public static int CRASH_WHOLE = 6;
    
    
    
    public VisibleObject(CrashCourse crashCourse, VisibleObjects deatils) {
        this.details = deatils;
        appearance = new ImageView(details.getImages().get(0));
        CrashCourse.addToScreen(appearance);
        
        setBorders();
       
        
        ObjectHandler.addToCurrentObjects(this);

        
      //  borderTesting(crashCourse);
    }
    
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        CrashCourse.addToScreen(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        setBorders();
        
        setPosition();
        
       // setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);

    }
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        CrashCourse.addToScreen(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        setBorders();
        
        setPosition();
        
       // setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);
        
        if(hasCollided() && removeOnCollision) {
            removeObject();
        }
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

    public double getxLocation() {
        return xLocation;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public void setyLocation(double yLocation) {
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
    

    private void setBorders() {
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
    
    private boolean hasCollided() {
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                return true;
            }
        }
        return false;
    }
    
    public int crashedInto(VisibleObject crasher) {
        if(crasher.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(crasher)) { 

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
         //  if(this instanceof Collectable)
               return CRASH_WHOLE;
        
        }
        return -1;
    }
    public boolean mayTurn(double degrees) {
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
    protected void setRotation(double degrees) {
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
    protected double getFacingRotation() {
        return rotation;
    }
    public double getBounciness() {
        return details.getBounciness();
    }
    private void borderTesting(CrashCourse crashcourse) {
        crashcourse.getRoot().getChildren().addAll(upBorder, rightBorder, downBorder, leftBorder);
        borders.setStroke(Color.ORANGE);
        upBorder.setStroke(Color.YELLOW);
        rightBorder.setStroke(Color.GREEN);
        downBorder.setStroke(Color.PINK);
        leftBorder.setStroke(Color.GREENYELLOW);
        upBorder.setStrokeWidth(2);
       // rightBorder.setStrokeWidth(10);
       // downBorder.setStrokeWidth(10);
       // leftBorder.setStrokeWidth(10);
    }

    protected VisibleObjects getDetails() {
        return details;
    }
    
    protected void changeAppearance(Image image) {
        appearance.setImage(image);
    }
    public void removeObject() {
        CrashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
        appearance.setDisable(true);
        borders.setDisable(true);
        upBorder.setDisable(true);
        downBorder.setDisable(true);
        leftBorder.setDisable(true);
        upBorder.setDisable(true);
    }
   // public void handleCrashedInto() {
        
   // }

    public double getCrashRepositioningMultiplicator() {
        return 1;
    }
}