/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author johanwendt
 */
public enum Players {
    PLAYER_ONE(0, 100, 100, 12, (float) 0.4, (float) 0.04, 2, 0, (float) 0.4);
    
    private final int startDirection, startTurningSpeed, baseRotate;
    private final float startXLocation, startYLocation, startSpeed, startAcceleration, startRetardation, slipperyTires;
    
    Players(int startDirection, float startXLocation, float startYLocation, float startSpeed, float startAcceleration, float startRetardation, int startTurningSpeed, int baseRotate, float slipperyTires) {
        this.startDirection = startDirection;
        this.startXLocation = startXLocation;
        this.startYLocation = startYLocation;
        this.startSpeed = startSpeed;
        this.startAcceleration = startAcceleration;
        this.startRetardation = startRetardation;
        this.startTurningSpeed = startTurningSpeed;
        this.baseRotate = baseRotate;
        this.slipperyTires = slipperyTires;
    }

    public int getStartDirection() {
        return startDirection;
    }

    public float getStartXLocation() {
        return startXLocation;
    }

    public float getStartYLocation() {
        return startYLocation;
    }

    public float getStartSpeed() {
        return startSpeed;
    }

    public float getStartAcceleration() {
        return startAcceleration;
    }

    public float getStartRetardation() {
        return startRetardation;
    }

    public int getStartTurningSpeed() {
        return startTurningSpeed;
    }

    public int getBaseRotate() {
        return baseRotate;
    }

    public float getSlipperyTires() {
        return slipperyTires;
    }
    
}
