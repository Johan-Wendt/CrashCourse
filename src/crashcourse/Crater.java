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
public class Crater extends VisibleObject {

    public Crater(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
        handlePutOnTop();
    }

    private boolean handlePutOnTop() {
            for(VisibleObject object : ObjectHandler.getCurrentObjects()) {
            if(putOnTop(object) && object instanceof Hinder) {
 //               object.getAppearance().toFront();
            }
        }
        return false;
    }

    
}
