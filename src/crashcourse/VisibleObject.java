/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author johanwendt
 */
public abstract class VisibleObject implements Constants{
    private VisibleObjects details;
    private double xLocation, yLocation, rotation;
    private SVGPath borders;
    public static int CRASH_UP = 0;
    public static int CRASH_RIGHT = 1;

    public static int CRASH_WHOLE = 6;
    
    private boolean crashWhole = true;
    private boolean changedAppearance;
    
    private static int currentObjectNumber = 0;
    private final int objectNumber;
    private int imageNumber;
    
    private int playAudio = -1;
    private int audioVolume = 100;
    
    public VisibleObject(VisibleObjects deatils) {
        this.details = deatils;
        imageNumber = deatils.getBaseImageNumber();
        setBorders();
       
        ObjectHandler.addToCurrentObjects(this);
        objectNumber = currentObjectNumber;
        increaseObjectNumber();

    }
    
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        imageNumber = deatils.getBaseImageNumber();
        setBorders();
        
        setPosition();
        
        ObjectHandler.addToCurrentObjects(this);
        objectNumber = currentObjectNumber;
        increaseObjectNumber();
    }
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        imageNumber = deatils.getBaseImageNumber();
        setBorders();
        setPosition();
        
        ObjectHandler.addToCurrentObjects(this);
        if(hasBeenPutOnTopOfOtherItem() && removeOnCollision) {
            removeObject();
        }
        objectNumber = currentObjectNumber;
        increaseObjectNumber();
    }
    
    public VisibleObject(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision, boolean crashable) {
        this.details = deatils;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        imageNumber = deatils.getBaseImageNumber();
        ObjectHandler.addToCurrentObjects(this);
      //  if(crashable) {
            setBorders();
            setPosition();
            if(hasBeenPutOnTopOfOtherItem() && removeOnCollision) {
                removeObject();
    //        }
          //  ObjectHandler.addToCurrentObjects(this);
        }
      //  else {
     //       setPositionWithoutBorders();
     //   }
        objectNumber = currentObjectNumber;
        increaseObjectNumber();
    }
    
    protected void setPosition() {
        if(borders != null) {
            borders.setTranslateX(xLocation);
            borders.setTranslateY(yLocation);
        }
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
    public SVGPath getBorders() {
        return borders;
    }
    private void setBorders() {
        borders = new SVGPath();
        borders.setContent(details.getSVGData());
    }
    private boolean hasBeenPutOnTopOfOtherItem() {
        if(borders != null) {
            for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
                if(putOnTop(object)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean putOnTop(VisibleObject object) {
        if(borders != null) {
            if(object.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(object)) { 

                Shape intersects = Shape.intersect(object.getBorders(), getBorders());

                if(intersects.getBoundsInParent().getWidth() !=  -1) {
                    return true;
                }
            }
        }
        return false;
    }
    public int crashedInto(MovingObject crasher) {
        if(borders != null) {
            if(crasher.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(crasher)) {    
                Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());

                if(intersects.getBoundsInParent().getWidth() > intersects.getBoundsInParent().getHeight()) {
                    return CRASH_UP;
                }
                else if(intersects.getBoundsInParent().getWidth() < intersects.getBoundsInParent().getHeight()) {
                    return CRASH_RIGHT;
                }
            }
        }
        return -1;
    }
    public boolean mayTurn(double degrees) {
        if(borders != null) {
            borders.setRotate(degrees);
            for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
                if(object.getBorders().getBoundsInParent().intersects(borders.getBoundsInParent()) && !this.equals(object)) {
                    borders.setRotate(rotation);
                    return false;
                }
            }
            borders.setRotate(rotation);
        }
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
        if(borders != null) {
            borders.setRotate(rotation);
        }
    }
    protected double getFacingRotation() {
        return rotation;
    }
    public double getBounciness() {
        return details.getBounciness();
    }
    protected VisibleObjects getDetails() {
        return details;
    }

    public void removeObject() {
   //     getAppearance().setBlendMode(BlendMode.ADD);
   //     Client.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
   //     appearance.setDisable(true);
        if(borders != null) {
            borders.setDisable(true);
        }
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
        currentObjectNumber ++;
    }
    public int getObjectNumber() {
        return objectNumber;
    }
    public int getImageNumber() {
        return imageNumber;
    }
    public void setImageNumber(int number) {
        imageNumber = number;
    }

    public boolean isChangedAppearance() {
        return changedAppearance;
    }

    public void setChangedAppearance(boolean changedAppearance) {
        this.changedAppearance = changedAppearance;
    }

    public int getPlayAudio() {
        return playAudio;
    }

    public void setPlayAudio(int playAudio) {
        this.playAudio = playAudio;
        audioVolume = 100;
    }
    public void setPlayAudio(int playAudio, int volume) {
        this.playAudio = playAudio;
        audioVolume = volume;
    }

    public int getAudioVolume() {
        return audioVolume;
    }
    
    
}