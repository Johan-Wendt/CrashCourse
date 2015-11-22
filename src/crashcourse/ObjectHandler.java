/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.HashSet;

/**
 *
 * @author johanwendt
 */
public class ObjectHandler {
    private static final HashSet<VisibleObject> currentObjects = new HashSet<>();
    
    public ObjectHandler() {
        
    }
    public static void addToCurrentObjects(VisibleObject object) {
        currentObjects.add(object);
    }

    public static HashSet<VisibleObject> getCurrentObjects() {
        return currentObjects;
    }
}
