/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author johanwendt
 */
public class ObjectHandler {
    private static final HashSet<VisibleObject> currentObjects = new HashSet<>();
    private static final HashSet<Collectable> currentCollectables = new HashSet<>();
    private static final HashSet<MovingObject> currentMovingObjects = new HashSet<>();
    
    private static final HashSet<VisibleObject> objectsToAddToClient = new HashSet<>();
    private static final HashSet<VisibleObject> objectsToRemoveFromClient = new HashSet<>();
    
    public ObjectHandler() {
        
    }
    public static void addToCurrentObjects(VisibleObject object) {
        currentObjects.add(object);
        objectsToAddToClient.add(object);
    }

    public static HashSet<VisibleObject> getCurrentObjects() {
        return currentObjects;
    }
    public static void removeFromCurrentObjects(VisibleObject object) {
        currentObjects.remove(object);
        objectsToRemoveFromClient.remove(object);
    }
    
    public static void addToCurrentMovingObjects(MovingObject object) {
        currentMovingObjects.add(object);
    }
    public static HashSet<MovingObject> getCurrentMovingObjects() {
        return currentMovingObjects;
    }
    public static void removeFromCurrentMovingObjects(MovingObject object) {
        currentMovingObjects.remove(object);
    }
    
    public static void addToCollectables(Collectable collectable) {
        currentCollectables.add(collectable);
    }
    public static void removeFromCollectables(Collectable collectable) {
        currentCollectables.remove(collectable);
    }
    public static HashSet<Collectable> getCurrentCollectables() {
        return currentCollectables;
    }
    
    public static void resetAllObjects() {
        HashSet<VisibleObject> toRemove = new HashSet<>(currentObjects);
        for(VisibleObject object : toRemove) {
            object.removeObject();
        }
        currentObjects.clear();
        currentCollectables.clear();
        currentMovingObjects.clear();
    }
    public static void clearAddAndRemoveClient() {
        objectsToAddToClient.clear();
        objectsToRemoveFromClient.clear();
    }

    public static HashSet<VisibleObject> getObjectsToAddToClient() {
        return objectsToAddToClient;
    }

    public static HashSet<VisibleObject> getObjectsToRemoveFromClient() {
        return objectsToRemoveFromClient;
    }
    
}
