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
    private double yMovingDirection, xMovingDirection, maxSpeed, currentSpeed, acceleration, driftingXDirection, driftingYDirection;
    private int maximumMaxSpeed;
    
    public MovingObject(CrashCourse crashCourse, VisibleObjects deatils) {
        super(crashCourse, deatils);
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
}
