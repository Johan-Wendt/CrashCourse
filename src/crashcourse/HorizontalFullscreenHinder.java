/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.scene.image.ImageView;

/**
 *
 * @author johanwendt
 */
public class HorizontalFullscreenHinder extends Hinder {

    public HorizontalFullscreenHinder(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
        
    }
    public HorizontalFullscreenHinder(VisibleObjects deatils, double xLocation, double yLocation, boolean upsideDown) {
        super(deatils, xLocation, yLocation);
        if(upsideDown) {
            getAppearance().setRotate(180);
            getBorders().setTranslateY(getyLocation() + getDetails().getHeight() / 2);
        }
        
        
    }
    
}
