/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

/**
 *
 * @author johanwendt
 */
public abstract class MovingObject extends VisibleObject {
    private double yMovingDirection, xMovingDirection, maxSpeed, currentSpeed, acceleration, driftingXDirection, driftingYDirection, beforeMoveX, beforeMoveY, retardation;
    private int maximumMaxSpeed;
    private AudioHandler audioHandler = new AudioHandler();
    
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
        if(crashe instanceof MovingObject) {
            System.out.println("MOVING");
            bumpInto(getInvertDriftingXDirection(), getInvertDriftingYDirection(), crashe);
        }
        else if(crashSort == VisibleObject.CRASH_UP || crashSort == VisibleObject.CRASH_DOWN) {
            System.out.println("UP");
            bumpInto(getDriftingXDirection(), getInvertDriftingYDirection(), crashe);
        }
        else if(crashSort == VisibleObject.CRASH_RIGHT || crashSort == VisibleObject.CRASH_LEFT) {
            System.out.println("Before" + driftingXDirection);
            bumpInto(getInvertDriftingXDirection(), getDriftingYDirection(), crashe);
            System.out.println("After" + driftingXDirection);
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
    
}
