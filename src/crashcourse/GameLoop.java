/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.animation.AnimationTimer;

/**
 *
 * @author johanwendt
 */
public class GameLoop extends AnimationTimer {
    private CrashCourse crashCourse;
    
    public GameLoop(CrashCourse crashCourse) {
        this.crashCourse = crashCourse;
    }

    @Override
    public void handle(long now) {
        crashCourse.getPlayerOne().act();
        crashCourse.getPlayerTwo().act();
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
        super.stop();
    }
}
