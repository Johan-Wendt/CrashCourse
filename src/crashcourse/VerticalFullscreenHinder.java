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
public class VerticalFullscreenHinder extends Hinder {

    public VerticalFullscreenHinder(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
    }
        public VerticalFullscreenHinder(VisibleObjects deatils, double xLocation, double yLocation, boolean upsideDown) {
        super(deatils, xLocation, yLocation);
        if(upsideDown) {
            getAppearance().setRotate(180);
            getBorders().setTranslateX(getxLocation() + getDetails().getWidth() / 2);
        }
    }


    
}
