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
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

/**
 *
 * @author johanwendt
 */
public class Explosion extends VisibleObject implements TimedEvent{
    private Timeline timeline;
    private SVGPath north, northEast, east, southEast, south, southWest, west, northWest;
    
    private static final int NORTH = 0;
    private static final int NORTH_EAST = 45;
    private static final int EAST = 90;
    private static final int SOUTH_EAST = 135;
    private static final int SOUTH = 180;
    private static final int SOUTH_WEST = 225;
    private static final int WEST = 270;
    private static final int NORTH_WEST = 315;

    public Explosion(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
        adjustLocation();
        setSpecialBorders();

        getAppearance().setBlendMode(BlendMode.COLOR_DODGE);
        setTimer(1000);
    }

    @Override
    public void act() {
    }

    @Override
    public void setTimer(double time) {
        timeline = new Timeline(new KeyFrame(Duration.millis(time),
        ae -> removeObject()));
        timeline.play();
    }
    public void removeObject() {
       // getAppearance().setBlendMode(BlendMode.ADD);
        super.removeObject();
        if(timeline != null) timeline.stop();

    }

    private void adjustLocation() {
        setxLocation(getxLocation() - getDetails().getWidth() / 2);
        setyLocation(getyLocation() - getDetails().getHeight() / 2);
        setPosition();
    }

    private void setSpecialBorders() {
        north = new SVGPath();
        northEast = new SVGPath();
        east = new SVGPath();
        southEast = new SVGPath();
        south = new SVGPath();
        southWest = new SVGPath();
        west = new SVGPath();
        northWest = new SVGPath();
        north.setContent("M 100,69 L 100,0");
        northEast.setContent("M 100,69 L 200,0");
        east.setContent("M 100,69 L 200,69");
        southEast.setContent("M 100,69 L 200,138");
        south.setContent("M 100,69 L 100,138");
        southWest.setContent("M 100,69 L 0,138");
        west.setContent("M 100,69 L 0,69");
        northWest.setContent("M 100,69 L 0,0");
        
        north.setTranslateX(getxLocation());
        north.setTranslateY(getyLocation());
        northEast.setTranslateX(getxLocation());
        northEast.setTranslateY(getyLocation());
        east.setTranslateX(getxLocation());
        east.setTranslateY(getyLocation());
        southEast.setTranslateX(getxLocation());
        southEast.setTranslateY(getyLocation());
        south.setTranslateX(getxLocation());
        south.setTranslateY(getyLocation());
        southWest.setTranslateX(getxLocation());
        southWest.setTranslateY(getyLocation());
        west.setTranslateX(getxLocation());
        west.setTranslateY(getyLocation());
        northWest.setTranslateX(getxLocation());
        northWest.setTranslateY(getyLocation());
        
        north.setStrokeWidth(10);
        northEast.setStrokeWidth(10);
        east.setStrokeWidth(10);
        southEast.setStrokeWidth(10);
        south.setStrokeWidth(10);
        southWest.setStrokeWidth(10);
        west.setStrokeWidth(10);
        northWest.setStrokeWidth(10);

    }
    public int crashedIntoOrientation(MovingObject crashe) {
        int linesCrossed = 0;
        int total = 0;
        if(crashe.getBorders().intersects(north.getBoundsInParent())) {
            linesCrossed ++;
            total += NORTH;
        }
        if(linesCrossed == 0) {
            return -1;
        }
        return total / linesCrossed;
    }
}
