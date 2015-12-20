/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *
 * @author johanwendt
 */
public class Explosion extends VisibleObject implements TimedEvent {
    private Timeline timeline;

    public Explosion(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
        setPlayAudio(SOUND_EXPLOSION);
        adjustLocation();
       // setSpecialBorders();

   //     getAppearance().setBlendMode(BlendMode.COLOR_DODGE);
        setTimer(1000);
    }


    @Override
    public void setTimer(double time) {
        timeline = new Timeline(new KeyFrame(Duration.millis(time),
        ae -> dieOut()));
        timeline.play();
    }

    private void adjustLocation() {
        setxLocation(getxLocation() - (getDetails().getWidth() / 2) - VisibleObjects.BOMB.getWidth() / 2);
        setyLocation(getyLocation() - (getDetails().getHeight() / 2) - VisibleObjects.BOMB.getHeight() / 2);
        setPosition();
    }

    @Override
    public int crashedInto(MovingObject crasher) {
      //  if(crasher.getBorders().getBoundsInParent().intersects(getBorders().getBoundsInParent()) && !this.equals(crasher)) { 
        if(crasher.getLastBombedBy() != this && !this.equals(crasher)) {            
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            if(intersects.getBoundsInParent().getWidth() != -1) {
                crasher.setHasBeenBombed(MovingObject.getMovingXDirectionFromCoordinats(crasher.getMiddleX(), crasher.getMiddleY(), getMiddleX(), getMiddleY()), MovingObject.getMovingYDirectionFromCoordinats(crasher.getMiddleX(), crasher.getMiddleY(), getMiddleX(), getMiddleY()), this);
                
                
                return CRASH_WHOLE;
            }
        
        }
        return -1;
    }
    private void dieOut() {
        Crater crater = new Crater(VisibleObjects.CRATER, this.getMiddleX() - (VisibleObjects.CRATER.getWidth() / 2), this.getMiddleY() - (VisibleObjects.CRATER.getHeight()/ 2));
        removeObject();
    }

    
    public void removeObject() {
  //      getAppearance().setImage(VisibleObjects.PLAYER_ONE.getImages().get(0));
        super.removeObject();
        if(timeline != null) timeline.stop();

    }
}
