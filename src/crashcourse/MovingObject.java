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
    private float yDirection, xDirection, maxSpeed, currentSpeed, acceleration;
    
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
    protected float getXDirection() {
        return xDirection;
    }
    protected float getYDirection() {
        return yDirection;
    }
    protected void setXDirection(float newDirection) {
        xDirection = newDirection;
    }
    protected void setYDirection(float newDirection) {
        yDirection = newDirection;
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
    
}
