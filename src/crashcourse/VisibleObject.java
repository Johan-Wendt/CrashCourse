/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private SVGPath borders;  //, upBorder, rightBorder, downBorder, leftBorder;
    public static int CRASH_UP = 0;
    public static int CRASH_RIGHT = 1;
    public static int CRASH_DOWN = 2;
    public static int CRASH_LEFT = 3;
    public static int CRASH_DAMAGING = 4;
    public static int CRASH_HARMLESS = 5;
    public static int CRASH_WHOLE = 6;
    
    private boolean crashWhole = true;
    
    private static int objectNumber = 0;
    
    
    
    public VisibleObject(VisibleObjects deatils) {
        this.details = deatils;
        appearance = new ImageView(details.getImages().get(0));
        appearance.toFront();
        Client.addToScreen(appearance);
        
        setBorders();
       
        
        ObjectHandler.addToCurrentObjects(this);
        increaseObjectNumber();

        
      //  borderTesting();
    }
    
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        appearance.toFront();
        Client.addToScreen(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        setBorders();
        
        setPosition();
        
       // setBorders(crashCourse);
        
        ObjectHandler.addToCurrentObjects(this);
        increaseObjectNumber();
        
     //   borderTesting();

    }
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        appearance.toFront();
        Client.addToScreen(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        setBorders();
        setPosition();
        
        ObjectHandler.addToCurrentObjects(this);
        if(hasBeenPutOnTopOfOtherItem() && removeOnCollision) {
            removeObject();
        }
        increaseObjectNumber();
    }
    
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision, boolean crashable) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        appearance = new ImageView(details.getImages().get(0));
        appearance.toFront();
        Client.addToScreen(appearance);
        appearance.setTranslateX(xLocation);
        appearance.setTranslateY(yLocation);
        
        if(crashable) {
            setBorders();
            setPosition();
            if(hasBeenPutOnTopOfOtherItem() && removeOnCollision) {
                removeObject();
            }
            ObjectHandler.addToCurrentObjects(this);
        }
        else {
            setPositionWithoutBorders();
        }
        increaseObjectNumber();
    }
    
    protected void setPosition() {
        appearance.setTranslateX(getxLocation());
        appearance.setTranslateY(getyLocation());
        borders.setTranslateX(xLocation);
        borders.setTranslateY(yLocation);
    }
    protected void setPositionWithoutBorders() {
        appearance.setTranslateX(getxLocation());
        appearance.setTranslateY(getyLocation());

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


    private void setBorders() {
        borders = new SVGPath();
        borders.setContent(details.getSVGData());

    }
    private boolean hasBeenPutOnTopOfOtherItem() {
            for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(putOnTop(object)) {
                return true;
            }
        }
        return false;
    }

    public boolean putOnTop(VisibleObject object) {
        if(object.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(object)) { 
                 
            Shape intersects = Shape.intersect(object.getBorders(), getBorders());
            
            if(intersects.getBoundsInParent().getWidth() !=  -1) {
                return true;
            }
        }
        return false;
    }
    public int crashedInto(MovingObject crasher) {
        if(crasher.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(crasher)) { 
           // if(this instanceof MovingObject) {
          //      return CRASH_WHOLE;
          //  }
                    
                    
                    
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            
            if(intersects.getBoundsInParent().getWidth() > intersects.getBoundsInParent().getHeight()) {
              //  System.out.println("up" + this.getClass());
              //  System.out.println(intersects.getBoundsInParent().getWidth());
                return CRASH_UP;
            }
            else if(intersects.getBoundsInParent().getWidth() < intersects.getBoundsInParent().getHeight()) {
             //   System.out.println("right" + this.getClass());
             //   System.out.println(intersects.getBoundsInParent().getHeight());
                return CRASH_RIGHT;
            }

        
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
        /**
        upBorder.setRotate(rotation);
        rightBorder.setRotate(rotation);
        downBorder.setRotate(rotation);
        leftBorder.setRotate(rotation);
        * */
    }
    protected double getFacingRotation() {
        return rotation;
    }
    public double getBounciness() {
        return details.getBounciness();
    }
    private void borderTesting() {
        Client.addToScreen(borders);

    }

    protected VisibleObjects getDetails() {
        return details;
    }
    
    protected void changeAppearance(Image image) {
        appearance.setImage(image);
    }
    public void removeObject() {
        getAppearance().setBlendMode(BlendMode.ADD);
        Client.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
        appearance.setDisable(true);
        borders.setDisable(true);

    }
    public double getMiddleX() {
        return getxLocation() + (details.getWidth() / 2);
    }
    public double getMiddleY() {
        return getyLocation() + (details.getHeight() / 2);
    }
   

    public double getCrashRepositioningMultiplicator() {
        return 1;
    }
    protected void setCrashWhole(boolean crashWhole) {
        this.crashWhole = crashWhole;
    }
    private static void increaseObjectNumber() {
        objectNumber ++;
    }
}