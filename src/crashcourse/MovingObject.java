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
    private float yMovingDirection, xMovingDirection, maxSpeed, currentSpeed, acceleration;
    
    public MovingObject(CrashCourse crashCourse, VisibleObjects deatils) {
        super(crashCourse, deatils);
        currentSpeed = 0;
    }

    public MovingObject(CrashCourse crashCourse, VisibleObjects deatils, float xLocation, float yLocation, float startSpeed, float acceleration) {
        super(crashCourse, deatils, xLocation, yLocation);
        maxSpeed = startSpeed;
        this.acceleration = acceleration;
        currentSpeed = 0;
    }
    protected float getXMovingDirection() {
        return xMovingDirection;
    }
    protected float getYMovingDirection() {
        return yMovingDirection;
    }
    protected void setXMovingDirection(float newDirection) {
        xMovingDirection = newDirection;
    }
    protected void setYMovingDirection(float newDirection) {
        yMovingDirection = newDirection;
    }
    public float getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    protected float  getInvertXDirection() {
        return - xMovingDirection;
    }
    protected float getInvertYDirection() {
        return - yMovingDirection;
    }
    protected float getMovingRotation() {
        float slideDirection = (float) Math.toDegrees(Math.atan2(getXMovingDirection(), getYMovingDirection()));
        slideDirection = 180 - slideDirection;
        return slideDirection;
    }
}
