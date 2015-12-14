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
    PLAYER_ONE(0, 100, CrashCourse.getGameHeight() / 2, 5, 0.1, 0.04, 3, 0, 0.4, 20, 45, 100),
    PLAYER_TWO(0, CrashCourse.getGameWidth() - 100, CrashCourse.getGameHeight() / 2, 5, 0.1, 0.04, 3, 0, 0.4, 20, 45, 100);
    
    private final int startDirection, startTurningSpeed, baseRotate, standardDesliding, maximumWheelTurnangle;
    private final double startXLocation, startYLocation, startSpeed, startAcceleration, startRetardation, slipperyTires, maxLife;
    
    Players(int startDirection, double startXLocation, double startYLocation, double startSpeed, double startAcceleration, double startRetardation, int startTurningSpeed, int baseRotate, double slipperyTires, int standardDesliding, int maximumWheelTurnangle, int maxLife) {
        this.startDirection = startDirection;
        this.startXLocation = startXLocation;
        this.startYLocation = startYLocation;
        this.startSpeed = startSpeed;
        this.startAcceleration = startAcceleration;
        this.startRetardation = startRetardation;
        this.startTurningSpeed = startTurningSpeed;
        this.baseRotate = baseRotate;
        this.slipperyTires = slipperyTires;
        this.standardDesliding = standardDesliding;
        this.maximumWheelTurnangle = maximumWheelTurnangle;
        this.maxLife = maxLife;
    }

    public int getStartDirection() {
        return startDirection;
    }

    public double getStartXLocation() {
        return startXLocation;
    }

    public double getStartYLocation() {
        return startYLocation;
    }

    public double getStartSpeed() {
        return startSpeed;
    }

    public double getStartAcceleration() {
        return startAcceleration;
    }

    public double getStartRetardation() {
        return startRetardation;
    }

    public int getStartTurningSpeed() {
        return startTurningSpeed;
    }

    public int getBaseRotate() {
        return baseRotate;
    }

    public double getSlipperyTires() {
        return slipperyTires;
    }
    public int getStandardDesliding() {
        return standardDesliding;
    }

    public int getMaximumWheelTurnangle() {
        return maximumWheelTurnangle;
    }

    public double getMaxLife() {
        return maxLife;
    }
    
}
