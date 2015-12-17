/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author johanwendt
 */
public class LifeMeter extends VisibleObject implements TimedEvent{

    private Timeline timeline;
    private Rectangle lifeLeft;
    
    public LifeMeter(VisibleObjects deatils, double xLocation, double yLocation, boolean removeOnCollision, boolean crashable, double lifeFactor) {
        super(deatils, xLocation, yLocation, removeOnCollision, crashable);
        showLifeMeter(lifeFactor);
        setTimer(500);

    }
    private void showLifeMeter(double lifeFactor) {
        double meterHeight = lifeFactor * getDetails().getHeight();
        lifeLeft = new Rectangle(getxLocation(), getyLocation() + (getDetails().getHeight() - meterHeight), getDetails().getWidth(), lifeFactor * getDetails().getHeight());
        lifeLeft.setFill(Color.GREEN);
        if(lifeFactor < 0.4) {
            lifeLeft.setFill(Color.YELLOW);
        }
        if(lifeFactor < 0.2) {
            lifeLeft.setFill(Color.RED);
        }
        Client.addToScreen(lifeLeft);
        getAppearance().toFront();
    }

    @Override
    public void setTimer(double time) {
        timeline = new Timeline(new KeyFrame(Duration.millis(time),
        ae -> removeObject()));
        timeline.play();
    }

    public void removeObject() {
        getAppearance().setBlendMode(BlendMode.ADD);
        Client.removeFromScreen(getAppearance());
        Client.removeFromScreen(lifeLeft);
        ObjectHandler.removeFromCurrentObjects(this);
        getAppearance().setDisable(true);
        if(timeline != null) timeline.stop();
    }
    
}
