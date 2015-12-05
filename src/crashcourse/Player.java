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
    private boolean isTurningRight, isTurningLeft, isMovingForward, isReversing, hasBumbed, isSliding, hasBeenCrashedInto;
    private HashMap<KeyCode, Integer> controls = new HashMap<>();
    private int turningSpeed, bumpCounter, baseRotate, bumpDeactivationFrequency, slideTimer, rockGrip, damageLevel, lastCrashSort;
    private double retardation, slippeyTires, bumpX, bumpY, bumpFactor, steepTurning, wheelAngle, wheelRotation, beforeMoveX, beforeMoveY, slidingXDirection, slidingYDirection, slideRotationMultiplicator, beforeTurn, speedBeforeMove, crashXDirection, crashYDirection, crashSpeed, relativeCrashSpeed;
    private MovingObject lastCrashe;

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
        setXMovingDirection(Math.sin(Math.toRadians(getFacingRotation())));
        setYMovingDirection(- Math.cos(Math.toRadians(getFacingRotation())));
        retardation = playerDetails.getStartRetardation();
        slippeyTires = playerDetails.getSlipperyTires();
        bumpX = bumpY = bumpFactor = wheelAngle = bumpCounter = damageLevel = 0;
        bumpDeactivationFrequency = playerDetails.getStandardDesliding();
        wheelRotation = playerDetails.getBaseRotate();
    }

    @Override
    public void act() {
        handleCrashedInto();
        turnWheels();
        turn();
        takeAction();
        setPosition();
        hasCollided();
       // deBump();
        bumpCounter ++;
    }
    private void turnWheels() {
        double wheelAngleBefore = wheelAngle;
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
            
            double speedFactor = getCurrentSpeed() / getMaxSpeed();
            if(isReversing && getCurrentSpeed() == 0) speedFactor = 0.1;
            double angleToTurn = 0;
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

            setXMovingDirection(Math.sin(Math.toRadians(getFacingRotation())));
            setYMovingDirection(- Math.cos(Math.toRadians(getFacingRotation())));
            
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
    
    private boolean hasCollided() {
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                rePosition(object.getCrashRepositioningMultiplicator());
                if(crashSort != VisibleObject.CRASH_HARMLESS) handleCrash(crashSort, object);
                return true;
            }
        }
        return false;
    }
    
    private void deBump() {
        if(hasBumbed && bumpCounter % bumpDeactivationFrequency == 0) {
            if(bumpFactor > 0) bumpFactor -= slippeyTires;
            if(bumpFactor < 0) {
                bumpFactor = 0;
                hasBumbed = false;
                double speedReductionFactor = Math.abs(180 - (Math.abs(getFacingRotation() - getMovingRotation()))) / 180;
                setCurrentSpeed(speedReductionFactor * getCurrentSpeed());
            }
        }
    }
    
    private void slide() {
        setRotation(getFacingRotation() + slideRotationMultiplicator);
        setXMovingDirection(Math.sin(Math.toRadians(getFacingRotation())));
        setYMovingDirection(- Math.cos(Math.toRadians(getFacingRotation())));
        
        setxLocation(getxLocation() + getCurrentSpeed() * slidingXDirection);
        setyLocation(getyLocation() + getCurrentSpeed() * slidingYDirection);
        
        slideTimer --;
        if(slideTimer < 1) stopSlide();
        
    }
    
    private void moveForward() {
        float noSlidePart = (float) (1.0 - bumpFactor);

        setxLocation(getxLocation() + getCurrentSpeed() * (noSlidePart * getXMovingDirection() + bumpFactor * bumpX));
        setyLocation(getyLocation() + getCurrentSpeed() * (noSlidePart * getYMovingDirection() + bumpFactor * bumpY));

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
    private void retardate(double multiplicator) {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - multiplicator * retardation);
    }
    private void startSlide(double multiplicator) {
        AudioHandler.playSqueek(0.1);
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
//Math.min(crashe.getBounciness(), this.getBounciness())
    private void wallCollide(double movingXDirection, double movingYDirection, VisibleObject crashe) {
        if(crashe instanceof Hinder) setCurrentSpeed(crashe.getBounciness() * getCurrentSpeed());
        bumpDeactivationFrequency = (int) (crashe.getBounciness() * playerDetails.getStandardDesliding());
        bumpX = movingXDirection;
        bumpY = movingYDirection;
        hasBumbed = true;
        bumpFactor = 1.0;
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
        else if(crashe instanceof Collectable) {
            handleCollectable((Collectable) crashe);
            if(crashe instanceof Bomb) {
                regularBump(crashSort, crashe);
            }
        }
        else {
            regularBump(crashSort, crashe);
        }
    }
    private void regularBump(int crashSort, VisibleObject crashe) {
        if(getRelativeSpeed() > 0.3) {
            AudioHandler.playThud(getRelativeSpeed());
        }
        if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
                wallCollide(getXMovingDirection(), getInvertYDirection(), crashe);
            }
            else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
                wallCollide(getInvertXDirection(), getYMovingDirection(), crashe);
            }
    }
    public void handleCrashedInto() {
        if(hasBeenCrashedInto) {
            if(lastCrashSort == VisibleObject.CRASH_DAMAGING) {
                if(crashSpeed >= 6) {
                    getHurt();
                    AudioHandler.playCrash();
                }
            }
            else if (lastCrashSort == VisibleObject.CRASH_HARMLESS) {
                if(getRelativeSpeed() > 0.3) AudioHandler.playThud(relativeCrashSpeed);
            }
            setCurrentSpeed(crashSpeed);
            wallCollide(crashXDirection, crashYDirection, lastCrashe);
            hasBeenCrashedInto = false;
        }
    }
    
    public void setHasBeenCrashedInto(int crashSort, MovingObject crashe) {
        lastCrashe = crashe;
        lastCrashSort = crashSort;
        crashXDirection = crashe.getXMovingDirection();
        crashYDirection = crashe.getYMovingDirection();
        crashSpeed = crashe.getCurrentSpeed();
        relativeCrashSpeed = crashe.getRelativeSpeed();
        hasBeenCrashedInto = true;
    }

/**    
    public void removePlayer(CrashCourse crashCourse) {
        crashCourse.removeFromScreen(getAppearance());
        ObjectHandler.removeFromCurrentObjects(this);
    }
    **/
    private double getWheelRotation() {
        return wheelRotation;
    }

    private void rePosition(double distance) {
        setRotation(beforeTurn);
        setxLocation(getxLocation() - ((getxLocation() - beforeMoveX)) * distance);
        setyLocation(getyLocation() - ((getyLocation() - beforeMoveY)) * distance);
        setPosition();
    }
    /**
    private void rePosition() {
        setRotation(beforeTurn);
        setxLocation(beforeMoveX);
        setyLocation(beforeMoveY);
        setPosition();
    }
    * **/
    
    @Override
    public int crashedInto(VisibleObject crasher) {
        
        int crashSort = -1;
        //if(crasher.getBorders().getBoundsInParent().intersects(getBorders().getBoundsInParent()) && !this.equals(crasher)) {
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            if(intersects.getBoundsInLocal().getWidth() != -1 && !this.equals(crasher)) {
                if(!(crasher instanceof MovingObject)) {
                    return VisibleObject.CRASH_HARMLESS;
                }
                Shape intersectsFront = Shape.intersect(crasher.getBorders(), getUpBorders());
                Shape frontIntersector = Shape.intersect(crasher.getUpBorders(), getBorders());
                if(intersectsFront.getBoundsInLocal().getWidth() == -1 && frontIntersector.getBoundsInLocal().getWidth() != -1) {
                    crashSort = VisibleObject.CRASH_DAMAGING;
                    setHasBeenCrashedInto(crashSort, (MovingObject) crasher);
                    return crashSort;
                }
                else {
                    crashSort = VisibleObject.CRASH_HARMLESS;
                    setHasBeenCrashedInto(crashSort, (MovingObject) crasher);
                    return crashSort;
                }
            }
          //  crashSort = VisibleObject.CRASH_HARMLESS;
       // }
        return crashSort;
    }
    private void getHurt() {
        damageLevel ++;
        if(damageLevel < getDetails().getImages().size()) {
            changeAppearance(getDetails().getImages().get(damageLevel));
        }
        
    }
    private void handleCollectable(Collectable collectable) {
        collectable.removeObject();
        if(collectable instanceof UppgradeBonus) {
            collectUppbgradeBonus(collectable.getCollectableNumber());
        }
        if(collectable instanceof HappeningCollectable) {
            handleHappeningCollectable(collectable);
        }
    }
    private void collectUppbgradeBonus(int collectable) {
        switch(collectable) {
            case Collectable.MAKE_FASTER_BONUS: 
                makeFaster();
        }
    }

    private void makeFaster() {
        setMaxSpeed(getMaxSpeed() + 0.5);
    }

    private void handleHappeningCollectable(Collectable collectable) {
        switch(collectable.getCollectableNumber()) {
            case Collectable.BOMB_COLLECTABLE: 
                Bomb.setLastBombLocation(collectable.getxLocation(), collectable.getyLocation());
                createBomb();
        }
    }

    private void createBomb() {
        //VisibleObjects deatils, double xLocation, double yLocation, double startSpeed, double movingXDirection, double movingYDirection
        Bomb bomb = new Bomb(VisibleObjects.BOMB, Bomb.getLastBombXLocation(), Bomb.getLastBombYLocation(), getCurrentSpeed(), getXMovingDirection(), getYMovingDirection());
    }
}
