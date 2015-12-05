/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author johanwendt
 */
public abstract class Collectable extends VisibleObject {
    private Collectables collectableDetails;
    private int longevity;
    private float birth;
    Timeline timeline;
    
    public static final int MAKE_FASTER_BONUS = 0;
    public static final int BOMB_COLLECTABLE = 1;

    public Collectable(VisibleObjects deatils, double xLocation, double yLocation, Collectables collectableDetails, boolean removeOnCollision) {
        super(deatils, xLocation, yLocation, removeOnCollision);
        this.collectableDetails = collectableDetails;
        Random random = new Random();
        longevity = collectableDetails.getLongevityMin() + random.nextInt(collectableDetails.getLongevityMax() - collectableDetails.getLongevityMin());
        CollectableHandler.addCollectable(this);
        
        timeline = new Timeline(new KeyFrame(Duration.millis(longevity),
        ae -> removeObject()));
        timeline.play();
    }
    @Override
    public void act() {

    }

    protected Collectables getCollectableDetails() {
        return collectableDetails;
    }
    public int getCollectableNumber() {
        return collectableDetails.getCollectableNumber();
    }
    @Override
    public void removeObject() {
        super.removeObject();
        if(timeline != null) timeline.stop();
        //CrashCourse.removeFromScreen(getAppearance());
       // ObjectHandler.removeFromCurrentObjects(this);
        ObjectHandler.removeFromCollectables(this);
        
    }
}
