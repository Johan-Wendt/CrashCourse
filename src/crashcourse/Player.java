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
    private int turningSpeed, bumpCounter, baseRotate, bumpDeactivationFrequency, slideTimer, rockGrip, damageLevel;
    private double slippeyTires, bumpX, bumpY, bumpFactor, steepTurning, wheelAngle, wheelRotation, slidingXDirection, slidingYDirection, slideRotationMultiplicator, beforeTurn, speedBeforeMove, crashXDirection, crashYDirection, crashSpeed, relativeCrashSpeed, maxAfterBumpSpeed, afterBumpSpeed, bumpRotation;
    private MovingObject lastCrashe;

    public Player(VisibleObjects deatils, Players playerDetails) {
        super(deatils);
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
        setRetardation(playerDetails.getStartRetardation());
        slippeyTires = playerDetails.getSlipperyTires();
        bumpX = bumpY = bumpFactor = wheelAngle = bumpCounter = damageLevel = 0;
        bumpRotation = 0; 
        bumpDeactivationFrequency = playerDetails.getStandardDesliding();
        wheelRotation = playerDetails.getBaseRotate();
        setCrashWhole(false);
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
       
    private void takeAction() {
        recordBeforeMovePosition();
        speedBeforeMove = getCurrentSpeed();
        

        if(hasBumbed) {
            bumpRotate();
            goWithTheFlow();
            deBump();
            retardate();
        }
        else if(isSliding) {
            slide();
        }
        else if(isMovingForward) {
            moveForward();
          //  deBump();
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
            setDriftingXDirection((getxLocation() - getBeforeMoveX()) / speedBeforeMove);
            setDriftingYDirection((getyLocation() - getBeforeMoveY()) / speedBeforeMove);
        }
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

    
    private void deBump() {
        if(getCurrentSpeed() == 0) {
            bumpEnded();
        }
        if(isMovingForward && afterBumpSpeed < maxAfterBumpSpeed) {
            afterBumpSpeed += playerDetails.getStartAcceleration() * 2;
        }
        if(hasBumbed && bumpCounter % bumpDeactivationFrequency == 0) {
            if(bumpFactor > 0) bumpFactor -= slippeyTires;
            if(bumpFactor < 0) {
                bumpEnded();
            }
        }
    }
    private void bumpEnded() {
        bumpFactor = 0;
        hasBumbed = false;
        double speedReductionFactor = Math.abs(180 - (Math.abs(getFacingRotation() - getMovingRotation()))) / 180;
        setCurrentSpeed(speedReductionFactor * afterBumpSpeed);
        afterBumpSpeed = 0;
    }
    
    private void bumpRotate() {
     //   System.out.println(getFacingRotation());
       // System.out.println(bumpRotation);
        setRotation(getFacingRotation() + bumpRotation);
        setMovingXAndYFromRotation(getFacingRotation());
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
        setxLocation(getxLocation() + getCurrentSpeed() * getXMovingDirection());
        setyLocation(getyLocation() + getCurrentSpeed() * getYMovingDirection());

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
    private void startSlide(double multiplicator) {
        getAudioHandler().playSqueek(0.1);
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
    @Override
    protected void bumpInto(double movingXDirection, double movingYDirection, VisibleObject crashe) {
        if(crashe instanceof Hinder) setCurrentSpeed(crashe.getBounciness() * getCurrentSpeed());
        bumpDeactivationFrequency = (int) (crashe.getBounciness() * playerDetails.getStandardDesliding());
        bumpX = movingXDirection;
        bumpY = movingYDirection;
        hasBumbed = true;
        maxAfterBumpSpeed = getCurrentSpeed();
        bumpFactor = 1.0;
        bumpCounter = 0;
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);
        setDriftingXDirection(movingXDirection);
        setDriftingYDirection(movingYDirection);

        steepTurning = 0;
    }
/**
    @Override
    protected void handleCrash(int crashSort, VisibleObject crashe) {
        
        if(crashe instanceof Collectable) {
            handleCollectable((Collectable) crashe);
            if(crashe instanceof Bomb) {
                regularBump(crashSort, crashe);
            }
        }
        else {
            regularBump(crashSort, crashe);
        }
    }
    * **/

    public void handleCrashedInto() {
        if(hasBeenCrashedInto) {
            if(crashSpeed >= 6) {
                getHurt();
                getAudioHandler().playCrash();
            }
            else {
                if(getRelativeSpeed() > 0.3) getAudioHandler().playThud(relativeCrashSpeed);
            }
            setCurrentSpeed(crashSpeed);
            bumpInto(crashXDirection, crashYDirection, lastCrashe);
            hasBeenCrashedInto = false;
        }
    }
    
    public void setHasBeenCrashedInto(MovingObject crashe, double rotation) {
        lastCrashe = crashe;
        crashXDirection = crashe.getXMovingDirection();
        crashYDirection = crashe.getYMovingDirection();
        crashSpeed = crashe.getCurrentSpeed();
        relativeCrashSpeed = crashe.getRelativeSpeed();
        bumpRotation = relativeCrashSpeed * crashe.getDetails().getWeightFactor() * rotation;
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

    @Override
    protected void rePosition(double distance) {
        setRotation(beforeTurn);
        super.rePosition(distance);
    }

    
    @Override
    public int crashedInto(MovingObject crasher) {
      //  if(crasher.getBorders().getBoundsInParent().intersects(getBorders().getBoundsInParent()) && !this.equals(crasher)) { 
        if(!this.equals(crasher)) {            
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            if(intersects.getBoundsInParent().getWidth() != -1) {
                double mediumX = (intersects.getBoundsInParent().getMaxX() + intersects.getBoundsInParent().getMinX()) / 2;
                double mediumY = (intersects.getBoundsInParent().getMaxY() + intersects.getBoundsInParent().getMinY()) / 2;
                double rotationDifference = getRotationDifferenceFromMovingDirection(mediumX, mediumY);
                setHasBeenCrashedInto(crasher, baseTurnDegreeOnCrash(rotationDifference));
                
                
                
                return CRASH_WHOLE;
            }
        
        }
        return -1;
    }
    private double getRotationDifferenceFromMovingDirection(double x, double y) {
        double newDirection = getRotationFromMovingDirection(x, y);
        double difference = newDirection - getFacingRotation();
        if(difference < 0) {
            difference += 360;
        }
        return difference;
    }

    private void getHurt() {
        damageLevel ++;
        if(damageLevel < getDetails().getImages().size()) {
            changeAppearance(getDetails().getImages().get(damageLevel));
        }
        
    }
    @Override
    protected void handleCollectable(Collectable collectable) {
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
    private double baseTurnDegreeOnCrash(double crashOrientation) {
        double baseTurn = -1;
        double sideHit = (getDetails().getWidth() / getDetails().getHeight()) * 90;
        if(crashOrientation > 180) {
            crashOrientation -= 180;
        }
        if(crashOrientation > sideHit && crashOrientation <= 180 - sideHit) {
            baseTurn = crashOrientation - 90.0; 
        }
        System.out.println(baseTurn);
        baseTurn = baseTurn / (Math.sqrt(Math.abs(baseTurn)));
        System.out.println(baseTurn);
        
        return baseTurn;
    }

    public Players getPlayerDetails() {
        return playerDetails;
    }
    
}
