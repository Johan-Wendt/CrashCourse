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
public class Bomb extends MovingNPO {
    private static double lastBombXLocation, lastBombYLocation, explosionThreshold;

    public Bomb(VisibleObjects deatils, double xLocation, double yLocation, double startSpeed, double movingXDirection, double movingYDirection) {
        super(deatils, xLocation, yLocation, startSpeed, movingXDirection, movingYDirection);
        setRetardation(0.01);
        getAudioHandler().playFuse();
        explosionThreshold = (startSpeed / 2) * Math.random();
    }
    public void act() {
        super.act();
        fuse();
        retardate();
    }
    
    public static void setLastBombLocation(double x, double y) {
        lastBombXLocation = x;
        lastBombYLocation = y;
    }

    public static double getLastBombXLocation() {
        return lastBombXLocation;
    }

    public static double getLastBombYLocation() {
        return lastBombYLocation;
    }

    @Override
    protected void bumpInto(double XDirection, double YDirection, VisibleObject crashe) {
        setDriftingXDirection(XDirection);
        setDriftingYDirection(YDirection);
    }

    @Override
    protected void handleCollectable(Collectable collectable) {
        if(collectable instanceof UppgradeBonus) {
            collectable.removeObject();
        }
    }

    private void fuse() {
        if(getCurrentSpeed() <= explosionThreshold) {
            explode();
        }
    }

    private void explode() {
        getAudioHandler().playExplosion();
        getAudioHandler().stopFuse();
        removeObject();
    }
    


}
