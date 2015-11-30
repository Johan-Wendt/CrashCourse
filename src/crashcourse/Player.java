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
    private boolean isTurningRight, isTurningLeft, isMovingForward, isReversing, hasBumbed;
    private HashMap<KeyCode, Integer> controls = new HashMap<>();
    private int turningSpeed, bumpCounter, baseRotate, bumpDeactivationFrequency;
    private float retardation, slippeyTires, bumpX, bumpY, bumpFactor, steepTurning, wheelAngle, wheelRotation, beforeMoveX, beforeMoveY;
        

    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails) {
        super(crashCourse, deatils);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = hasBumbed= false;
        setRotation(playerDetails.getStartDirection());
        turningSpeed = playerDetails.getStartTurningSpeed();
        baseRotate = playerDetails.getBaseRotate();
        setAcceleration(playerDetails.getStartAcceleration());
        setMaxSpeed(playerDetails.getStartSpeed());
        setxLocation(playerDetails.getStartXLocation());
        setyLocation(playerDetails.getStartYLocation());
        setControls(KeyCode.UP, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.DOWN);
        setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
        setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
        retardation = playerDetails.getStartRetardation();
        slippeyTires = playerDetails.getSlipperyTires();
        bumpX = bumpY = bumpFactor = wheelAngle = bumpCounter = 0;
        bumpDeactivationFrequency = playerDetails.getStandardDesliding();
        wheelRotation = playerDetails.getBaseRotate();
    }
    /**
    public Player(CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, float xLocation, float yLocation, float startSpeed, float acceleration, float retardation) {
        super(crashCourse, deatils, xLocation, yLocation, startSpeed, acceleration);
        this.playerDetails = playerDetails;
        isTurningLeft = isTurningRight = isMovingForward = false;
        facing = 0;
        turningSpeed = 8;
        bumpCounter = 0;
        baseRotate = 90;
        this.retardation = retardation;
    }
**/
    @Override
    public void act() {
        turnWheels();
        turn();
        takeAction();
        setPosition();
        checkCollision();
        deBump();
        bumpCounter ++;
      //  System.out.println(getCurrentSpeed());
    }
    private void takeAction() {
        beforeMoveX = getxLocation();
        beforeMoveY = getyLocation();      
        

        if(isMovingForward) {
            moveForward();
            accelerate();
        }
        else if(isReversing) {
            if(getCurrentSpeed() != 0) {
                moveForward();
                retardate(2);
            }
            else {
                moveBackWards();
            }
        }
        else {
            moveForward();
            retardate();
        }
        
    }
    private void moveForward() {
        float noSlidePart = (float) (1.0 - bumpFactor);
        setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXMovingDirection() + bumpFactor * bumpX));
        setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYMovingDirection() + bumpFactor * bumpY));
      //  setxLocation(getxLocation() + getCurrentSpeed() * getXMovingDirection());
      //  setyLocation(getyLocation() + getCurrentSpeed() * getYMovingDirection());
    }
    private void moveBackWards() {
        setxLocation(getxLocation() + getInvertXDirection());
        setyLocation(getyLocation() + getInvertYDirection());
    }
    private void accelerate() {
        if(getCurrentSpeed() < getMaxSpeed()) setCurrentSpeed(getCurrentSpeed() + getAcceleration());
    }
    private void retardate() {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - retardation);
    }
    private void retardate(float multiplicator) {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - multiplicator * retardation);
    }

    public void setControls(KeyCode gas, KeyCode right, KeyCode left, KeyCode reverse) {
        controls.clear();
        controls.put(gas, PlayerControls.MOVE_FORWARD);
        controls.put(right, PlayerControls.TURN_RIGHT);
        controls.put(left, PlayerControls.TURN_LEFT);
        controls.put(reverse, PlayerControls.REVERSE);
        
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
                case PlayerControls.REVERSE: isReversing = true;
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
                case PlayerControls.REVERSE: isReversing = false;
                     break;    
            }
        }
    }
    private void turnWheels() {
        float wheelAngleBefore = wheelAngle;
        if(isTurningLeft) {
            wheelAngle -= turningSpeed;
            if(Math.abs(wheelAngle) > playerDetails.getMaximumWheelTurnangle()) {
                wheelAngle = -playerDetails.getMaximumWheelTurnangle();
            }
        }
        if(isTurningRight) {
            wheelAngle += turningSpeed;
            if(Math.abs(wheelAngle) > playerDetails.getMaximumWheelTurnangle()) {
                wheelAngle = playerDetails.getMaximumWheelTurnangle();
            }
        }
        wheelRotation += wheelAngle - wheelAngleBefore;
    }

    private void turn() {
        if(!hasBumbed && getCurrentSpeed() > 0 || isReversing) {
            boolean turned = false;
            float speedFactor = getCurrentSpeed() / getMaxSpeed();
            if(isReversing && getCurrentSpeed() == 0) speedFactor = (float) 0.1;
            float angleToTurn = speedFactor * turningSpeed * 2;
            if(wheelAngle < 0) {
                if(wheelAngle + angleToTurn > 0) angleToTurn = -wheelAngle;
                setRotation(getFacingRotation() - angleToTurn);
                wheelAngle += angleToTurn;
                turned = true;
            }
            else if(wheelAngle > 0) {
                if(wheelAngle + angleToTurn < 0) angleToTurn = wheelAngle;
                setRotation(getFacingRotation() + angleToTurn);
                wheelAngle -= angleToTurn;
                turned = true;
            }
            


            if(turned && !hasBumbed && speedFactor > 0.5) {
                steepTurning += turningSpeed;
            }
            else if (steepTurning > 0) {
                steepTurning -= turningSpeed;
            }

            if(speedFactor * steepTurning > 20 * turningSpeed) {
                slide(speedFactor);
            }
            setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
        }

    }
    private void slide(float speedFactor) {
        if(!hasBumbed) {
            System.out.println("is sliding");
            bumpDeactivationFrequency = playerDetails.getStandardDesliding();
            bumpX = speedFactor * ((float) Math.sin(Math.toRadians(getFacingRotation())));
            bumpY =speedFactor * ((float) - Math.cos(Math.toRadians(getFacingRotation())));
            hasBumbed = true;
            bumpFactor = (float) 0.6;
            bumpCounter = 0;
            setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
            
            steepTurning = 0;
        }
    }
    
    private void wallCollide(float movingXDirection, float movingYDirection, VisibleObject crashe) {

        setCurrentSpeed(crashe.getBounciness() * getCurrentSpeed());
        bumpDeactivationFrequency = (int) (Math.min(crashe.getBounciness(), this.getBounciness()) * playerDetails.getStandardDesliding());
        bumpX = movingXDirection;
        bumpY = movingYDirection;
        hasBumbed = true;
        bumpFactor = (float) 1.0;
        bumpCounter = 0;
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);        

        steepTurning = 0;
    }

    private void deBump() {
        if(hasBumbed && bumpCounter % bumpDeactivationFrequency == 0) {
            if(bumpFactor > 0) bumpFactor -= slippeyTires;
            if(bumpFactor < 0) {
                bumpFactor = 0;
                hasBumbed = false;
                float speedReductionFactor = Math.abs(180 - (Math.abs(getFacingRotation() - getMovingRotation()))) / 180;
                setCurrentSpeed(speedReductionFactor * getCurrentSpeed());
            }
        }
    }

    private void checkCollision() {
        loop: for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                rePosition();
                handleCrash(crashSort, object);
                break loop;
            }
        }
    }



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
    
    private float getWheelRotation() {
        return wheelRotation;
    }

    private void rePosition() {
        setxLocation(beforeMoveX);
        setyLocation(beforeMoveY);
    }
}
