/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author johanwendt
 */
public class LifeMeter extends VisibleObject implements TimedEvent{

    private Timeline timeline;
    private int meterXLocation, meterYLocation, meterWidth, meterHeight, lifeFactor;
    
    public LifeMeter(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision, boolean crashable, double lifeFactor) {
        super(deatils, xLocation, yLocation, removeOnCollision, crashable);
        createLifeMeter(lifeFactor);
        setTimer(500);

    }
    private void createLifeMeter(double lifeFactor) {
        
        meterHeight = (int) (lifeFactor * getDetails().getHeight());
        meterWidth = getDetails().getWidth();
        meterXLocation = (int) getxLocation();
        meterYLocation = (int) (getyLocation() + (getDetails().getHeight() - meterHeight));
        this.lifeFactor = (int) (lifeFactor * 100);
        /**
        lifeLeft = new Rectangle(getxLocation(), getyLocation() + (getDetails().getHeight() - meterHeight), getDetails().getWidth(), lifeFactor * getDetails().getHeight());
        lifeLeft.setFill(Color.GREEN);
        if(lifeFactor < 0.4) {
            lifeLeft.setFill(Color.YELLOW);
        }
        if(lifeFactor < 0.2) {
            lifeLeft.setFill(Color.RED);
        }
        addToScreen(lifeLeft);
     * */
    }

    @Override
    public void setTimer(double time) {
        timeline = new Timeline(new KeyFrame(Duration.millis(time),
        ae -> removeObject()));
        timeline.play();
    }

    public void removeObject() {
    //    getAppearance().setBlendMode(BlendMode.ADD);
    //    Client.removeFromScreen(getAppearance());
     //   Client.removeFromScreen(lifeLeft);
        ObjectHandler.removeFromCurrentObjects(this);
     //   getAppearance().setDisable(true);
        if(timeline != null) timeline.stop();
    }

    public int getMeterXLocation() {
        return meterXLocation;
    }

    public int getMeterYLocation() {
        return meterYLocation;
    }

    public int getMeterWidth() {
        return meterWidth;
    }

    public int getMeterHeight() {
        return meterHeight;
    }

    public int getLifeFactor() {
        return lifeFactor;
    }
    
}
