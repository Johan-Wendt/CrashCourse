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
        explosionThreshold = (startSpeed / 2) * Math.random();
        setPlayAudio(SOUND_FUSE);
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
      //  AudioHandler.playFuse();
        if(getCurrentSpeed() <= explosionThreshold) {
            explode();
        }
    }

    private void explode() {
        //getAudioHandler().playExplosion();
       // setPlayAudio(SOUND_EXPLOSION);
        removeObject();
        Explosion explosion = new Explosion(VisibleObjects.EXPLOSION, getxLocation(), getyLocation());
    }
    public void removeObject() {
        //getAudioHandler().stopFuse();
      //  setPlayAudio(SOUND_STOP_FUSE);
        super.removeObject();
        
    }


}
