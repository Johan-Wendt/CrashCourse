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
public abstract class Collectable extends VisibleObject {
    private Collectables collectableDetails;
    private static int Longevity;

    public Collectable(CrashCourse crashCourse, VisibleObjects deatils, float xLocation, float yLocation, Collectables collectableDetails) {
        super(crashCourse, deatils, xLocation, yLocation);
        this.collectableDetails = collectableDetails;
        Longevity = Ransomiserat tal mellan min och max
    }
    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected Collectables getCollectableDetails() {
        return collectableDetails;
    }
    public int getCollectableNumber() {
        return collectableDetails.getCollectableNumber();
    }
}
