/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

/**
 *
 * @author johanwendt
 */
public class Player extends MovingObject{
    private Players playerDetails;
    private boolean isTurningRight, isTurningLeft, isMovingForward, isReversing, hasBumbed, isSliding;
    private HashMap<KeyCode, Integer> controls = new HashMap<>();
    private int turningSpeed, bumpCounter, baseRotate, bumpDeactivationFrequency, slideTimer, rockGrip, damageLevel;
    private float retardation, slippeyTires, bumpX, bumpY, bumpFactor, steepTurning, wheelAngle, wheelRotation, beforeMoveX, beforeMoveY, slidingXDirection, slidingYDirection, slideRotationMultiplicator, beforeTurn, speedBeforeMove;
        

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
        setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
        setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
        retardation = playerDetails.getStartRetardation();
        slippeyTires = playerDetails.getSlipperyTires();
        bumpX = bumpY = bumpFactor = wheelAngle = bumpCounter = damageLevel = 0;
        bumpDeactivationFrequency = playerDetails.getStandardDesliding();
        wheelRotation = playerDetails.getBaseRotate();
    }

    @Override
    public void act() {
        turnWheels();
        turn();
        takeAction();
        setPosition();
        checkCollision();
       // deBump();
        bumpCounter ++;
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
        beforeTurn = getFacingRotation();
        if((!hasBumbed && !isSliding && getCurrentSpeed() > 0) || (isReversing && getCurrentSpeed() == 0)) {
            
            float speedFactor = getCurrentSpeed() / getMaxSpeed();
            if(isReversing && getCurrentSpeed() == 0) speedFactor = (float) 0.1;
            float angleToTurn = 0;
            if(wheelAngle != 0) {
                angleToTurn = speedFactor * turningSpeed * 2 * (wheelAngle/(Math.abs(wheelAngle)));
                if(Math.abs(angleToTurn) > Math.abs(wheelAngle)) angleToTurn = wheelAngle;
                setRotation(getFacingRotation() + angleToTurn);
                wheelAngle -= angleToTurn;
            }


            if(speedFactor >= 0.9 && rockGrip < 0) {
                steepTurning += Math.abs(angleToTurn);
                if(steepTurning >= 35) startSlide(angleToTurn);
            }
            else {
                steepTurning = 0;
            }

            setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
            
            rockGrip --;
        }

    }    
    private void takeAction() {
        beforeMoveX = getxLocation();
        beforeMoveY = getyLocation();
        speedBeforeMove = getCurrentSpeed();
        

        if(isSliding) {
            slide();
        }
        else if(isMovingForward) {
            moveForward();
            deBump();
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
            deBump();
        }
        else {
            moveForward();
            if(hasBumbed) retardate(3);
            else retardate();
        }
        if(speedBeforeMove != 0) {
            setDriftingXDirection((getxLocation() - beforeMoveX) / speedBeforeMove);
            setDriftingYDirection((getyLocation() - beforeMoveY) / speedBeforeMove);
        }
    }
    
    private void checkCollision() {
        loop: for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                rePosition();
                if(crashSort != VisibleObject.CRASH_HARMLESS) handleCrash(crashSort, object);
                break loop;
            }
        }
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
    
    private void slide() {
        setRotation(getFacingRotation() + slideRotationMultiplicator);
        setXMovingDirection((float) Math.sin(Math.toRadians(getFacingRotation())));
        setYMovingDirection((float) - Math.cos(Math.toRadians(getFacingRotation())));
        
        setxLocation(getxLocation() + getCurrentSpeed() * slidingXDirection);
        setyLocation(getyLocation() + getCurrentSpeed() * slidingYDirection);
        
        slideTimer --;
        if(slideTimer < 1) stopSlide();
        
    }
    
    private void moveForward() {
        float noSlidePart = (float) (1.0 - bumpFactor);
        
      //  System.out.println("x_: " + getxLocation());
      //  System.out.println("y: " + getyLocation());
     //   System.out.println("noSlidePart: " + noSlidePart);
     //   System.out.println("getXMovingDirection(): " + getXMovingDirection());
     //   System.out.println("getYMovingDirection(): " + getYMovingDirection());
     //   System.out.println("bumpFactor: " + bumpFactor);
     //   System.out.println("bumpX: " + bumpX);
     //   System.out.println("bumpY: " + bumpY);
        setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXMovingDirection() + bumpFactor * bumpX));
        setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYMovingDirection() + bumpFactor * bumpY));

      //  setxLocation(getxLocation() + getCurrentSpeed() * getXMovingDirection());
      //  setyLocation(getyLocation() + getCurrentSpeed() * getYMovingDirection());
    }
    private void moveBackWards() {
        setxLocation(getxLocation() + getInvertXDirection());
        setyLocation(getyLocation() + getInvertYDirection());
    }
    private void goWithTheFlow() {
        setxLocation(getxLocation() + getCurrentSpeed() * getDriftingXDirection());
        setyLocation(getyLocation() + getCurrentSpeed() * getDriftingYDirection());
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
    private void startSlide(float multiplicator) {
        AudioHandler.playSqueek();
        steepTurning = 0;
        isSliding = true;
        slideRotationMultiplicator = multiplicator;
        slidingXDirection = getXMovingDirection();
        slidingYDirection = getYMovingDirection();
        slideTimer = 20;
    }
    private void stopSlide() {
        isSliding = false;
        rockGrip = 50;
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

    private void wallCollide(float movingXDirection, float movingYDirection, VisibleObject crashe) {
        if(crashe instanceof Hinder) setCurrentSpeed(crashe.getBounciness() * getCurrentSpeed());
        bumpDeactivationFrequency = (int) (Math.min(crashe.getBounciness(), this.getBounciness()) * playerDetails.getStandardDesliding());
        bumpX = movingXDirection;
        bumpY = movingYDirection;
        hasBumbed = true;
        bumpFactor = (float) 1.0;
        bumpCounter = 0;
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);
        setDriftingXDirection(movingXDirection);
        setDriftingYDirection(movingYDirection);

        steepTurning = 0;
    }

    private void handleCrash(int crashSort, VisibleObject crashe) {
        if(crashe instanceof Player) {
            if(crashSort == VisibleObject.CRASH_UP) {
                wallCollide(getXMovingDirection(), getInvertYDirection(), crashe);
            }
            else {
                wallCollide(getInvertXDirection(), getYMovingDirection(), crashe);
            }
        }
        else {
            if(getRelativeSpeed() > 0.3) AudioHandler.playThud(getRelativeSpeed());
            if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
                wallCollide(getXMovingDirection(), getInvertYDirection(), crashe);
            }
            else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
                wallCollide(getInvertXDirection(), getYMovingDirection(), crashe);
            }
        }
    }
    public void handleCrashedInto(int crashSort, MovingObject crashe) {
        if(crashSort == VisibleObject.CRASH_DAMAGING) {
            if(crashe.getRelativeSpeed() > 0.8) {
                getHurt();
                AudioHandler.playCrash(crashe.getRelativeSpeed());
            }
        }
        else if (crashSort == VisibleObject.CRASH_HARMLESS) {
            if(getRelativeSpeed() > 0.3) AudioHandler.playThud(crashe.getRelativeSpeed());
        }
        setCurrentSpeed(crashe.getCurrentSpeed());
        wallCollide(crashe.getXMovingDirection(), crashe.getYMovingDirection(), crashe);
    }

/**    
    public void removePlayer(CrashCourse crashCourse) {
        crashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
    }
    **/
    private float getWheelRotation() {
        return wheelRotation;
    }

    private void rePosition() {
        setRotation(beforeTurn);
        setxLocation(beforeMoveX);
        setyLocation(beforeMoveY);
        setPosition();
    }
    
    @Override
    public int crashedInto(VisibleObject crasher) {
        int crashSort = -1;
      //  if(crasher.getBorders().getBoundsInParent().intersects(getBorders().getBoundsInParent()) && !this.equals(crasher)) {
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            if(intersects.getBoundsInLocal().getWidth() != -1 && !this.equals(crasher)) {
                Shape intersectsFront = Shape.intersect(crasher.getBorders(), getUpBorders());
                Shape frontIntersector = Shape.intersect(crasher.getUpBorders(), getBorders());
                if(intersectsFront.getBoundsInLocal().getWidth() == -1 && frontIntersector.getBoundsInLocal().getWidth() != -1) {
                    crashSort = VisibleObject.CRASH_DAMAGING;
                    handleCrashedInto(crashSort, (MovingObject) crasher);
                    return crashSort;
                }
                else {
                    crashSort = VisibleObject.CRASH_HARMLESS;
                    handleCrashedInto(crashSort, (MovingObject) crasher);
                    return crashSort;
                }
            }
          //  crashSort = VisibleObject.CRASH_HARMLESS;
      //  }
        return crashSort;
    }
    private void getHurt() {
        damageLevel ++;
        if(damageLevel < getDetails().getImages().size()) {
            changeAppearance(getDetails().getImages().get(damageLevel));
        }
        
    }
}
