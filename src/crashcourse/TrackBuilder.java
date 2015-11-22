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
public class TrackBuilder {
    private CrashCourse crashCourse;
    public TrackBuilder(CrashCourse crashCourse) {
        this.crashCourse = crashCourse;
    }
    public void buildStandardTrack() {
        createBoundaries();
    }

    private void createBoundaries() {
        createUpperboundary();
        createLowererboundary(); 
        createLeftboundary(); 
        createRightboundary(); 
    }

    private void createUpperboundary() {
        int x = 0;
        int y = 0;
        while(x < CrashCourse.getGameWidth()) {
            Hinder hinder = new Hinder(crashCourse, VisibleObjects.HINDER, x, y);
            x += VisibleObjects.HINDER.getWidth();
        }
    }

    private void createLowererboundary() {
        int x = 0;
        int y = CrashCourse.getGameHeight() - VisibleObjects.HINDER.getHeight();
        while(x < CrashCourse.getGameWidth()) {
            Hinder hinder = new Hinder(crashCourse, VisibleObjects.HINDER, x, y);
            x += VisibleObjects.HINDER.getWidth();
        }
    }

    private void createLeftboundary() {
        int x = 0;
        int y = 0;
        while(y < CrashCourse.getGameHeight()) {
            Hinder hinder = new Hinder(crashCourse, VisibleObjects.HINDER, x, y);
            y += VisibleObjects.HINDER.getHeight();
        }
    }

    private void createRightboundary() {
        int x = CrashCourse.getGameWidth()- VisibleObjects.HINDER.getWidth();;
        int y = 0;
        while(y < CrashCourse.getGameHeight()) {
            Hinder hinder = new Hinder(crashCourse, VisibleObjects.HINDER, x, y);
            y += VisibleObjects.HINDER.getHeight();
        }
    }
}
