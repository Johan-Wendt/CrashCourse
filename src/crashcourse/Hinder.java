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
public abstract class Hinder extends NeutralObject {

    public Hinder(VisibleObjects deatils, double xLocation, double yLocation) {
        super(deatils, xLocation, yLocation);
    }

    @Override
    public void act() {
    }
    
}
