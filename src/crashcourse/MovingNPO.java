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
public abstract class MovingNPO extends MovingObject {

    public MovingNPO(VisibleObjects deatils, double xLocation, double yLocation, double startSpeed) {
        super(deatils, xLocation, yLocation, startSpeed);
    }
    public MovingNPO(VisibleObjects deatils, double xLocation, double yLocation, double startSpeed, double movingXDirection, double movingYDirection) {
        super(deatils, xLocation, yLocation, startSpeed);
        setCurrentSpeed(startSpeed);
        setXMovingDirection(movingXDirection);
        setYMovingDirection(movingYDirection);
    }
    
    @Override
    public void act() {
        move();
        setPosition();
    }
    private void move() {
        setxLocation(getxLocation() + getCurrentSpeed() * getXMovingDirection());
        setyLocation(getyLocation() + getCurrentSpeed() * getYMovingDirection());

    }
}
