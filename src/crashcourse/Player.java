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
        setControls(KeyCode.UP, KeyCode.RIGHT, KeyCode.LEFT);
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
        setPosition();
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

    public void setControls(KeyCode gas, KeyCode right, KeyCode left) {
        controls.clear();
        controls.put(gas, PlayerControls.MOVE_FORWARD);
        controls.put(right, PlayerControls.TURN_RIGHT);
        controls.put(left, PlayerControls.TURN_LEFT);
        
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
        int beforeTurn = facing;
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
        if(checkTurnCollision()) {
            System.out.println("turncollide");
            facing = beforeTurn;
            setXDirection((float) Math.sin(Math.toRadians(facing)));
            setYDirection((float) - Math.cos(Math.toRadians(facing)));


            getAppearance().setRotate(baseRotate + facing);
        }
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
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                handleCrash(crashSort);
                break loop;
            }
        }
    }
    private boolean checkTurnCollision() {
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(object.turnedInto(this)) {
                return true;
            }
        }
        return false;
    }


    private void handleCrash(int crashSort) {
       // setCurrentSpeed((float) 0.1);
        if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
            invertYDirection();
        }
        else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
            invertXDirection();
        }
        float speedFactor = getCurrentSpeed() / getMaxSpeed();
        slideX = speedFactor * ((float) getXDirection());
        slideY =speedFactor * ((float) getYDirection());
        isSliding = true;
        slideFactor = (float) 1.0;
    }
    public void removePlayer(CrashCourse crashCourse) {
        crashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
    }
}
