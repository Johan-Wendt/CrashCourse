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
       // createUpperboundary();
       // createLowererboundary(); 
       // createLeftboundary(); 
       // createRightboundary(); 
        HorizontalFullscreenHinder hinderUp = new HorizontalFullscreenHinder(crashCourse, VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER, 0, 0);
        HorizontalFullscreenHinder hinderDown = new HorizontalFullscreenHinder(crashCourse, VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER, 0,660);
        VerticalFullscreenHinder hinderRight = new VerticalFullscreenHinder(crashCourse, VisibleObjects.VERTICAL_FULLSCREEN_HINDER, 1160, 40);
        VerticalFullscreenHinder hinderLeft = new VerticalFullscreenHinder(crashCourse, VisibleObjects.VERTICAL_FULLSCREEN_HINDER, 0, 40);
    }
    

    private void createUpperboundary() {
        int x = 0;
        int y = 0;
        while(x < CrashCourse.getGameWidth()) {
            SmallHinder hinder = new SmallHinder(crashCourse, VisibleObjects.SMALL_HINDER, x, y);
            x += VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getWidth();
        }
    }

    private void createLowererboundary() {
        int x = 0;
        int y = CrashCourse.getGameHeight() - VisibleObjects.SMALL_HINDER.getHeight();
        while(x < CrashCourse.getGameWidth()) {
            Hinder hinder = new SmallHinder(crashCourse, VisibleObjects.SMALL_HINDER, x, y);
            x += VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getWidth();
        }
    }

    private void createLeftboundary() {
        int x = 0;
        int y = 0;
        while(y < CrashCourse.getGameHeight()) {
            Hinder hinder = new SmallHinder(crashCourse, VisibleObjects.SMALL_HINDER, x, y);
            y += VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getHeight();
        }
    }

    private void createRightboundary() {
        int x = CrashCourse.getGameWidth()- VisibleObjects.SMALL_HINDER.getWidth();;
        int y = 0;
        while(y < CrashCourse.getGameHeight()) {
          //  Hinder hinder = new SmallHinder(crashCourse, VisibleObjects.SMALL_HINDER, x, y);
            VerticalFullscreenHinder hinder = new VerticalFullscreenHinder(crashCourse, VisibleObjects.VERTICAL_FULLSCREEN_HINDER, x, y);
            y += VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getHeight();
        }
    }
}
