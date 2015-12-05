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
    private static double lastBombXLocation, lastBombYLocation;

    public Bomb(VisibleObjects deatils, double xLocation, double yLocation, double startSpeed, double movingXDirection, double movingYDirection) {
        super(deatils, xLocation, yLocation, startSpeed, movingXDirection, movingYDirection);
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
    


}
