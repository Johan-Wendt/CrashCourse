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
public enum Collectables {
    MAKE_FASTER_BONUS (Collectable.MAKE_FASTER_BONUS, "WHEEL UP - Become faster", 10, 10000, 20000),
    BOMB_COLLECTABLE (Collectable.BOMB_COLLECTABLE, "BOMB - Watch out!", 10, 10000, 20000);

    
    private final String collectableDescription;
    private final int collectableProbabilityFactor, collectableNumber, longevityMin, longevityMax;
    
    
    Collectables(int bonusNumber , String bonusDescription, int bonusProbabilityFactor, int longevityMin, int longevityMax) {
    this.collectableDescription = bonusDescription;
    this.collectableProbabilityFactor = bonusProbabilityFactor;
    this.collectableNumber = bonusNumber;
    this.longevityMin = longevityMin;
    this.longevityMax = longevityMax;
    }

    public String getCollectableDescription() {
        return collectableDescription;
    }

    public int getCollectableProbabilityFactor() {
        return collectableProbabilityFactor;
    }
    public int getCollectableNumber() {
        return collectableNumber;
    }

    public int getLongevityMin() {
        return longevityMin;
    }
    public int getLongevityMax() {
        return longevityMax;
    }
}
