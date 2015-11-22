/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 *
 * @author johanwendt
 */
public abstract class VisibleObject {
    private VisibleObjects details;
    private float xLocation, yLocation;
    private ImageView appearance;
    private SVGPath borders;
    
    public VisibleObject(CrashCourse crashCourse, VisibleObjects deatils) {
        this.details = deatils;
        appearance = new ImageView(details.getImages().get(0));
        crashCourse.getRoot().getChildren().add(appearance);
        
        borders = new SVGPath();
        borders.setContent(details.getSVGData());
        
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
        
        borders = new SVGPath();
        borders.setContent(details.getSVGData());
        
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
    
}
