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
public class CollectableHandler {
    private static final int LIFESPAN_MIN = 5000;
    private static final int LIFESPAN_MAX = 20000;
    private static final double BONUS_PROBABILITY = 0.01;
    
    private CrashCourse crashCourse;

    
    public void CollectableHandler(CrashCourse crashCourse) {
        this.crashCourse = crashCourse;
    }
    public void act() {
        if(BONUS_PROBABILITY > Math.random()) createRandomCollectable();
    }
    private void createRandomCollectable() {
        createMakeFasterBonus();
    }
    private void createMakeFasterBonus() {
        MakeFasterBonus bonus = new MakeFasterBonus(crashCourse, VisibleObjects.PLAYER_ONE, LIFESPAN_MIN, LIFESPAN_MIN);
    }
}
