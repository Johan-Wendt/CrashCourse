/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.HashMap;
import javafx.scene.input.KeyCode;

/**
 *
 * @author johanwendt
 */
public class Player extends MovingObject{
    private Players playerDetails;
    private boolean isTurningRight, isTurningLeft, isMovingForward, isSliding;
    private HashMap<KeyCode, Integer> controls = new HashMap<>();
    private int turningSpeed, slideCounter, baseRotate, slideDeactivationFrequency;
    private float retardation, slippeyTires, slideX, slideY, slideFactor, steepTurning;
        

    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails) {
        super(crashCourse, deatils);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = isSliding= false;
        setRotation(playerDetails.getStartDirection());
        turningSpeed = playerDetails.getStartTurningSpeed();
        baseRotate = playerDetails.getBaseRotate();
        setAcceleration(playerDetails.getStartAcceleration());
        setMaxSpeed(playerDetails.getStartSpeed());
        setxLocation(playerDetails.getStartXLocation());
        setyLocation(playerDetails.getStartYLocation());
        setControls(KeyCode.UP, KeyCode.RIGHT, KeyCode.LEFT);
        retardation = playerDetails.getStartRetardation();
        slippeyTires = playerDetails.getSlipperyTires();
        slideX = slideY = slideFactor = slideCounter = 0;
        slideDeactivationFrequency = playerDetails.getStandardDesliding();
        
    }
    /**
    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, float xLocation, float yLocation, float startSpeed, float acceleration, float retardation) {
        super(crashCourse, deatils, xLocation, yLocation, startSpeed, acceleration);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = false;
        facing = 0;
        turningSpeed = 8;
        slideCounter = 0;
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
        deSlide();
        slideCounter ++;
    }
    private void setLocation() {
        float noSlidePart = (float) (1.0 - slideFactor);

        if(isMovingForward) {
            setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXMovingDirection() + slideFactor * slideX));
            setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYMovingDirection() + slideFactor * slideY));
            accelerate();
        }
        else {
            setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXMovingDirection() + slideFactor * slideX));
            setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYMovingDirection() + slideFactor * slideY));
            retardate();
        }
        
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
        boolean turned = false;
        float speedFactor = getCurrentSpeed() / getMaxSpeed();
        if(isTurningLeft && mayTurn(getFacingRotation() - turningSpeed)) {
            setRotation(getFacingRotation() - turningSpeed);
            turned = true;
        }
        if(isTurningRight && mayTurn(getFacingRotation() + turningSpeed)) {
            setRotation(getFacingRotation() + turningSpeed);
            turned = true;
        }
        if(turned && !isSliding) {
            steepTurning += turningSpeed;
        }
        else if (steepTurning > 0) {
            steepTurning -= turningSpeed;
        }

        if(speedFactor * steepTurning > 50 * turningSpeed) {
            slide(speedFactor);
        }
        if(!isSliding) {
            setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
        }

    }
    private void slide(float speedFactor) {
        if(!isSliding) {
            slideDeactivationFrequency = playerDetails.getStandardDesliding();
            slideX = speedFactor * ((float) Math.sin(Math.toRadians(getFacingRotation())));
            slideY =speedFactor * ((float) - Math.cos(Math.toRadians(getFacingRotation())));
            isSliding = true;
            slideFactor = (float) 1.0;
            slideCounter = 0;
            setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
            
            steepTurning = 0;
        }
    }
    
    private void wallCollide(float movingXDirection, float movingYDirection, VisibleObject crashe) {

        setCurrentSpeed(crashe.getBounciness() * getCurrentSpeed());
        slideDeactivationFrequency = (int) (Math.min(crashe.getBounciness(), this.getBounciness()) * playerDetails.getStandardDesliding());
        slideX = movingXDirection;
        slideY = movingYDirection;
        isSliding = true;
        slideFactor = (float) 1.0;
        slideCounter = 0;
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);        

        steepTurning = 0;
    }

    private void deSlide() {
        if(slideCounter % slideDeactivationFrequency == 0) {
            if(slideFactor > 0) slideFactor -= slippeyTires;
            if(slideFactor < 0) {
                slideFactor = 0;
                isSliding = false;
                float speedReductionFactor = Math.abs(180 - (Math.abs(getFacingRotation() - getMovingRotation()))) / 180;
          //      System.out.println("facing minus moving: " + Math.abs(getFacingRotation() - getMovingRotation()));
            //    System.out.println(speedReductionFactor);
                setCurrentSpeed(speedReductionFactor * getCurrentSpeed());
            }
        }
    }

    private void checkCollision() {
        loop: for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                handleCrash(crashSort, object);
                break loop;
            }
        }
    }
    /**
    private boolean checkTurnCollision() {
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(object.turnedInto(this)) {
                return true;
            }
        }
        return false;
    }
    * **/


    private void handleCrash(int crashSort, VisibleObject crashe) {
        if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
            wallCollide(getXMovingDirection(), getInvertYDirection(), crashe);
        }
        else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
            wallCollide(getInvertXDirection(), getYMovingDirection(), crashe);
        }
    }
    
    public void removePlayer(CrashCourse crashCourse) {
        crashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
    }
}
