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
public class HappeningCollectable extends Collectable {

    public HappeningCollectable(VisibleObjects deatils, double xLocation, double yLocation, Collectables collectableDetails, boolean removeOnCollision) {
        super(deatils, xLocation, yLocation, collectableDetails, removeOnCollision);
    }
    
}
