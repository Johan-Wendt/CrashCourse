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
    public MovingNPO(VisibleObjects deatils, double xLocation, double yLocation, double startSpeed, double driftingXDirection, double driftingYDirection) {
        super(deatils, xLocation, yLocation, startSpeed);
        setCurrentSpeed(startSpeed);
        setDriftingXDirection(driftingXDirection);
        setDriftingYDirection(driftingYDirection);
    }
    
    @Override
    public void act() {
        recordBeforeMovePosition();
        move();
        setPosition();
        hasCollided();
    }
    private void move() {
        setxLocation(getxLocation() + getCurrentSpeed() * getDriftingXDirection());
        setyLocation(getyLocation() + getCurrentSpeed() * getDriftingYDirection());
    }
    
}
