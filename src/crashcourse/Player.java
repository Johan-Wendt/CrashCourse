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
    private int turningSpeed, slideCounter, baseRotate;
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
        slideX = slideY = slideFactor = 0;
        slideCounter = 0;
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
        slideCounter ++;
    }
    private void setLocation() {
        float noSlidePart = (float) (1.0 - slideFactor);
    //    System.out.println("noslide" + (noSlidePart * getXMovingDirection()));
    //    System.out.println(("Sssslide" + slideFactor * slideX));
    //    System.out.println(("SssslideFactor" + slideFactor));
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
        boolean turned = false;
        float speedFactor = getCurrentSpeed() / getMaxSpeed();
        if(isTurningLeft && mayTurn(getRotation() - turningSpeed)) {
            setRotation(getRotation() - turningSpeed);
            turned = true;
        }
        if(isTurningRight && mayTurn(getRotation() + turningSpeed)) {
            setRotation(getRotation() + turningSpeed);
            turned = true;
        }
        if(turned) {
            steepTurning += turningSpeed;
        }
        else if (steepTurning > 0) {
            steepTurning -= turningSpeed;
        }
        if(speedFactor * steepTurning > 5 * turningSpeed) {
            slide(speedFactor);
        }
        if(!isSliding) {
            setXMovingDirection((float) Math.sin(Math.toRadians(getRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getRotation())));
        }

        
       // getAppearance().setRotate(baseRotate + facing);
        /**
        if(checkTurnCollision()) {
            System.out.println("turncollide");
            facing = beforeTurn;
            setXMovingDirection((float) Math.sin(Math.toRadians(facing)));
            setYMovingDirection((float) - Math.cos(Math.toRadians(facing)));


            getAppearance().setRotate(baseRotate + facing);
        }
        * **/
    }
    private void slide(float speedFactor) {
        if(!isSliding) {
            slideX = speedFactor * ((float) Math.sin(Math.toRadians(getRotation())));
            slideY =speedFactor * ((float) - Math.cos(Math.toRadians(getRotation())));
            isSliding = true;
            slideFactor = (float) 1.0;
            slideCounter = 0;
            setXMovingDirection((float) Math.sin(Math.toRadians(getRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getRotation())));
        }
    }
    
    private void wallCollide(float movingXDirection, float movingYDirection) {
        System.out.println("x" + movingXDirection);
        System.out.println("y" + movingYDirection);
        System.out.println("xBefore" + getXMovingDirection());
        System.out.println("yBefore" + getXMovingDirection());
        slideX = movingXDirection;
        slideY = movingYDirection;
        isSliding = true;
        slideFactor = (float) 1.0;
        slideCounter = 0;
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);
        
    }

    private void deSlide() {
        if(slideCounter % 20 == 0) {
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


    private void handleCrash(int crashSort) {
        if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
            //invertYDirection();
            wallCollide(getXMovingDirection(), getInvertYDirection());
        }
        else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
         //   invertXDirection();
            wallCollide(getInvertXDirection(), getYMovingDirection());
        }
    }
    
    public void removePlayer(CrashCourse crashCourse) {
        crashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
    }
}
