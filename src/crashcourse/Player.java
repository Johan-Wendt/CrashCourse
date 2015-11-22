/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author johanwendt
 */
public class Player extends MovingObject{
    private Players playerDetails;
    private boolean isTurningRight, isTurningLeft, isMovingForward, isSliding;
    private HashMap<KeyCode, Integer> controls = new HashMap<>();
    private int facing, turningSpeed, turnsPlayed, baseRotate;
    private float retardation, slippeyTires, slideX, slideY, slideFactor;
    
    private int bangForTheBuck = 0;
    

    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails) {
        super(crashCourse, deatils);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = isSliding= false;
        facing = playerDetails.getStartDirection();
        turningSpeed = playerDetails.getStartTurningSpeed();
        baseRotate = playerDetails.getBaseRotate();
        setAcceleration(playerDetails.getStartAcceleration());
        setMaxSpeed(playerDetails.getStartSpeed());
        setxLocation(playerDetails.getStartXLocation());
        setyLocation(playerDetails.getStartYLocation());
        retardation = playerDetails.getStartRetardation();
        slippeyTires = playerDetails.getSlipperyTires();
        slideX = slideY = slideFactor = 0;
        turnsPlayed = 0;
    }
    /**
    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, float xLocation, float yLocation, float startSpeed, float acceleration, float retardation) {
        super(crashCourse, deatils, xLocation, yLocation, startSpeed, acceleration);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = false;
        facing = 0;
        turningSpeed = 8;
        turnsPlayed = 0;
        baseRotate = 90;
        this.retardation = retardation;
    }
**/
    @Override
    public void act() {
        turn();
        setLocation();
        move();
        checkCollision();
        turnsPlayed ++;
    }
    private void setLocation() {
        float noSlidePart = (float) (1.0 - slideFactor);
        if(isMovingForward) {
            setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXDirection() + slideFactor * slideX));
            setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYDirection() + slideFactor * slideY));
            accelerate();
        }
        else {
            setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXDirection() + slideFactor * slideX));
            setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYDirection() + slideFactor * slideY));
            retardate();
        }
        deSlide();
    }
    private void accelerate() {
        if(getCurrentSpeed() < getMaxSpeed()) setCurrentSpeed(getCurrentSpeed() + getAcceleration());
        if(getCurrentSpeed() > getMaxSpeed()) setCurrentSpeed(getMaxSpeed());
    }
    private void retardate() {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - retardation);
        if(getCurrentSpeed() < 0) setCurrentSpeed(0);
    }
    private void move() {
        getAppearance().setTranslateX(getxLocation());
        getAppearance().setTranslateY(getyLocation());
    }
    public void setControls(KeyCode gas, KeyCode left, KeyCode right) {
        controls.clear();
        controls.put(gas, PlayerControls.MOVE_FORWARD);
        controls.put(left, PlayerControls.TURN_LEFT);
        controls.put(right, PlayerControls.TURN_RIGHT);
    }
    public void takeKeyPressed(KeyCode key) {
        if(controls.containsKey(key)) {
            int action = controls.get(key);
            switch(action) {
                case PlayerControls.MOVE_FORWARD: isMovingForward = true;
                     break;
                case PlayerControls.TURN_LEFT: isTurningLeft = true;
                     break;
                case PlayerControls.TURN_RIGHT: isTurningRight = true;
                     break;
            }
        }
    }
    public void takeKeyReleased(KeyCode key) {
        if(controls.containsKey(key)) {
            int action = controls.get(key);
            switch(action) {
                case PlayerControls.MOVE_FORWARD: isMovingForward = false;
                     break;
                case PlayerControls.TURN_LEFT: isTurningLeft = false;
                     break;
                case PlayerControls.TURN_RIGHT: isTurningRight = false;
                     break;
            }
        }
    }

    private void turn() {
        if(!isSliding) {
            float speedFactor = getCurrentSpeed() / getMaxSpeed();
            slideX = speedFactor * ((float) Math.sin(Math.toRadians(facing)));
            slideY =speedFactor * ((float) - Math.cos(Math.toRadians(facing)));
            isSliding = true;
            slideFactor = (float) 1.0;
        }
        if(isTurningLeft) {
            facing -= turningSpeed;
        }
        if(isTurningRight) {
            facing += turningSpeed;
        }
        setXDirection((float) Math.sin(Math.toRadians(facing)));
        setYDirection((float) - Math.cos(Math.toRadians(facing)));
        
        getAppearance().setRotate(baseRotate + facing);
    }

    private void deSlide() {
        if(turnsPlayed % 20 == 0) {
            if(slideFactor > 0) slideFactor -= slippeyTires;
            if(slideFactor < 0) {
                slideFactor = 0;
                isSliding = false;
            }
        }
    }

    private void checkCollision() {
        loop: for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(crash(object)) {
                handleCrash();
                break loop;
            }
        }
    }
    private boolean crash(VisibleObject object) {
        if(getAppearance().getBoundsInParent().intersects(object.getAppearance().getBoundsInParent()) && !this.equals(object)) {
            Shape intersection = SVGPath.intersect(getBorders(), object.getBorders()); 
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true; 
            }
        }
        return false; 
    }

    private void handleCrash() {
        System.out.println("Bang!" + bangForTheBuck);
        bangForTheBuck ++;
    }
}
