/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import static crashcourse.VisibleObject.CRASH_RIGHT;
import static crashcourse.VisibleObject.CRASH_UP;
import javafx.scene.shape.Shape;

/**
 *
 * @author johanwendt
 */
public abstract class MovingObject extends VisibleObject {
    private double yMovingDirection, xMovingDirection, maxSpeed, currentSpeed, acceleration, driftingXDirection, driftingYDirection, beforeMoveX, beforeMoveY, retardation;
    private int maximumMaxSpeed;
    private static AudioHandler audioHandler = new AudioHandler();
    private VisibleObject lastBombedBy;
    
    public MovingObject(VisibleObjects deatils) {
        super(deatils);
        currentSpeed = 0;
        maximumMaxSpeed = 10;
        ObjectHandler.addToCurrentMovingObjects(this);
    }
    public MovingObject(VisibleObjects deatils, double xLocation, double yLocation, double startMaxSpeed) {
        super(deatils, xLocation, yLocation);
        maxSpeed = startMaxSpeed;
        currentSpeed = 0;
        ObjectHandler.addToCurrentMovingObjects(this);
    }

    public MovingObject(VisibleObjects deatils, double xLocation, double yLocation, double startMaxSpeed, double acceleration) {
        super(deatils, xLocation, yLocation);
        maxSpeed = startMaxSpeed;
        this.acceleration = acceleration;
        currentSpeed = 0;
        ObjectHandler.addToCurrentMovingObjects(this);
    }
    public abstract void act();
    public double getXMovingDirection() {
        return xMovingDirection;
    }
    public double getYMovingDirection() {
        return yMovingDirection;
    }
    protected void setXMovingDirection(double newDirection) {
        xMovingDirection = newDirection;
    }
    protected void setYMovingDirection(double newDirection) {
        yMovingDirection = newDirection;
    }
    public double getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(double maxSpeed) {
        if(maxSpeed < maximumMaxSpeed) {
            this.maxSpeed = maxSpeed;
        }
        else { 
            maxSpeed = maximumMaxSpeed;
        }
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
        if(currentSpeed < 0) {
            this.currentSpeed = 0;
        }
        if(currentSpeed > maxSpeed) {
            this.currentSpeed = maxSpeed;
        }
    }
    protected double  getInvertXDirection() {
        return - xMovingDirection;
    }
    protected double getInvertYDirection() {
        return - yMovingDirection;
    }
    protected double  getInvertDriftingXDirection() {
        return - driftingXDirection;
    }
    protected double getInvertDriftingYDirection() {
        return - driftingYDirection;
    }
    protected double getMovingRotation() {
        double slideDirection = Math.toDegrees(Math.atan2(getXMovingDirection(), getYMovingDirection()));
        slideDirection = 180 - slideDirection;
        return slideDirection;
    }
    protected double getRotationFromMovingDirection(double x, double y) {
        double xDirection = getMovingXDirectionFromCoordinats(getMiddleX(), getMiddleY(), x, y);
        double yDirection = getMovingYDirectionFromCoordinats(getMiddleX(), getMiddleY(), x, y);
        double rotation = Math.toDegrees(Math.atan2(xDirection, yDirection));
        rotation = 180 - rotation;
        return rotation;
    }
    public static double getMovingXDirectionFromCoordinats(double x1, double y1, double x2, double y2) {
        double a = (x2 - x1);
        double b = (y2 - y1);
        double c = Math.sqrt((a*a) + (b*b));
        return a / c;
    }
    public static double getMovingYDirectionFromCoordinats(double x1, double y1, double x2, double y2) {
        double a = (x2 - x1);
        double b = (y2 - y1);
        double c = Math.sqrt((a*a) + (b*b));
        return b / c;
    }

    public double getyMovingDirection() {
        return yMovingDirection;
    }

    public void setyMovingDirection(double yMovingDirection) {
        this.yMovingDirection = yMovingDirection;
    }

    public double getxMovingDirection() {
        return xMovingDirection;
    }

    public void setxMovingDirection(double xMovingDirection) {
        this.xMovingDirection = xMovingDirection;
    }

    public double getDriftingXDirection() {
        return driftingXDirection;
    }

    public void setDriftingXDirection(double driftingXDirection) {
        this.driftingXDirection = driftingXDirection;
    }

    public double getDriftingYDirection() {
        return driftingYDirection;
    }

    public void setDriftingYDirection(double driftingYDirection) {
        this.driftingYDirection = driftingYDirection;
    }
    public double getRelativeSpeed() {
        return getCurrentSpeed() / getMaxSpeed();
    }
    @Override
    public double getCrashRepositioningMultiplicator() {
        return 1.5;
    }
    @Override
    public void removeObject() {
        super.removeObject();
        ObjectHandler.removeFromCurrentMovingObjects(this);
    }
    protected boolean hasCollided() {
        for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            int crashSort = object.crashedInto(this);
            if(crashSort >= 0 ) {
                rePosition(object.getCrashRepositioningMultiplicator());
                handleCrash(crashSort, object);
                return true;
            }
        }
        return false;
    }

    protected void setBeforeMoveX(double beforeMoveX) {
        this.beforeMoveX = beforeMoveX;
    }

    protected void setBeforeMoveY(double beforeMoveY) {
        this.beforeMoveY = beforeMoveY;
    }

    public double getBeforeMoveX() {
        return beforeMoveX;
    }

    public double getBeforeMoveY() {
        return beforeMoveY;
    }
    
    protected void rePosition(double distance) {
        setxLocation(getxLocation() - ((getxLocation() - beforeMoveX)) * distance);
        setyLocation(getyLocation() - ((getyLocation() - beforeMoveY)) * distance);
        setPosition();
    }
    protected void recordBeforeMovePosition() {
        beforeMoveX = getxLocation();
        beforeMoveY = getyLocation();
    }
    protected void regularBump(int crashSort, VisibleObject crashe) {
        if(getRelativeSpeed() > 0.3) {
            audioHandler.playThud(getRelativeSpeed());
        }
        if(crashSort == VisibleObject.CRASH_WHOLE) {
            bumpInto(getInvertDriftingXDirection(), getInvertDriftingYDirection(), crashe);
        }

        else if(crashSort == VisibleObject.CRASH_UP) {
            bumpInto(getDriftingXDirection(), getInvertDriftingYDirection(), crashe);
        }
        else if(crashSort == VisibleObject.CRASH_RIGHT) {
            bumpInto(getInvertDriftingXDirection(), getDriftingYDirection(), crashe);
        }
    }
    
    protected abstract void bumpInto(double movingXDirection, double movingYDirection, VisibleObject crashe);
    
    protected void handleCrash(int crashSort, VisibleObject crashe) {
        if(crashe instanceof Collectable) {
            handleCollectable((Collectable) crashe);
            if(crashe instanceof BombCollectable) {
                regularBump(crashSort, crashe);
            }
        }
        else {
            regularBump(crashSort, crashe);
        }
    }
    protected abstract void handleCollectable(Collectable collectable);

    public AudioHandler getAudioHandler() {
        return audioHandler;
    }
    protected void retardate() {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - retardation);
    }
    protected void retardate(double multiplicator) {
        if(getCurrentSpeed() > 0) setCurrentSpeed(getCurrentSpeed() - multiplicator * retardation);
    }

    public double getRetardation() {
        return retardation;
    }

    public void setRetardation(double retardation) {
        this.retardation = retardation;
    }
    
    protected void setMovingXAndYFromRotation(double rotation) {
        setXMovingDirection(Math.sin(Math.toRadians(rotation)));
        setYMovingDirection(- Math.cos(Math.toRadians(rotation)));
    }
    
    @Override
    public int crashedInto(MovingObject crasher) {
      
        if(!this.equals(crasher)) {            
            Shape intersects = Shape.intersect(crasher.getBorders(), getBorders());
            
            
            if(intersects.getBoundsInParent().getWidth() != -1) {
                return CRASH_WHOLE;
            }
            
            /**
            double width = intersects.getBoundsInParent().getWidth();
            double height = intersects.getBoundsInParent().getHeight();
            if(height != -1 && width != -1) {

                if(intersects.getBoundsInParent().getWidth() > intersects.getBoundsInParent().getHeight()) {
                    return CRASH_UP;
                }
                else if(intersects.getBoundsInParent().getWidth() < intersects.getBoundsInParent().getHeight()) {
                    return CRASH_RIGHT;
                }


            }
            * **/
            
        }
        return -1;
    }
    public void setHasBeenBombed(double directionX, double directionY, VisibleObject bomb) {
        if(lastBombedBy != bomb) {
            lastBombedBy = bomb;
            setCurrentSpeed(getMaxSpeed());
            bumpInto(directionX, directionY, bomb);
        }
    }

    public VisibleObject getLastBombedBy() {
        return lastBombedBy;
    }
    public void setLastBombedBy(VisibleObject bomb) {
        lastBombedBy = bomb;
    }

    

    
}
