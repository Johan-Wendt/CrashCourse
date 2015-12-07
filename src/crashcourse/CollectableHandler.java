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
public class CollectableHandler {
    private static final double BONUS_PROBABILITY = 0.01;
    private static final HashSet<Collectable> collectables = new HashSet<>();
    
    private static double makeFasterChance;
    private static double bombChance;
        
    public void CollectableHandler(CrashCourse crashCourse) {
        setProbabilityFactors();
    }
    public static void act() {
        if(BONUS_PROBABILITY > Math.random()) {
            createRandomCollectable();
        }
       // removeDeadCollectables();
    }
    private static void createRandomCollectable() {
        if(Math.random() < makeFasterChance) {
            createMakeFasterBonus();
        }
        else if(Math.random() < bombChance) {
            createBombCollectable();
        }
    }
    private static void createMakeFasterBonus() {
        MakeFasterBonus bonus = new MakeFasterBonus(VisibleObjects.MAKE_FASTER_BONUS, TrackBuilder.getRandomXInTrack(20), TrackBuilder.getRandomYInTrack(40), Collectables.MAKE_FASTER_BONUS, true);
    }
    private static void createBombCollectable() {
        BombCollectable bomb = new BombCollectable(VisibleObjects.BOMB, TrackBuilder.getRandomXInTrack(20), TrackBuilder.getRandomYInTrack(40), Collectables.BOMB_COLLECTABLE, true);
    }
    /**
    private static void removeDeadCollectables() {
        for(Collectable collectable : collectables) {
            collectable.act();
        }
    }
    * */
    public static void addCollectable(Collectable collectable) {
        collectables.add(collectable);
    }

    public static void setProbabilityFactors() {
        float sumOfAll = Collectables.MAKE_FASTER_BONUS.getCollectableProbabilityFactor() + Collectables.MAKE_FASTER_BONUS.getCollectableProbabilityFactor();
        makeFasterChance = Collectables.MAKE_FASTER_BONUS.getCollectableProbabilityFactor() / sumOfAll;
        bombChance = 1;
    }
}
