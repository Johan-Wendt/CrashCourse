/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.util.Random;

/**
 *
 * @author johanwendt
 */
public class TrackBuilder implements Constants{
    private static int minXInTrack;
    private static int maxXInTrack;
    private static int minYInTrack;
    private static int maxYInTrack;
    
    private static final int edgeThicknes = 40;
    
    private static final Random random = new Random();
    
    public TrackBuilder() {
    }
    public void buildStandardTrack() {
      //  edgeThicknes = 80;
        setTrackBoundaries();
        createBoundaries();
    }

    private void createBoundaries() {
        HorizontalFullscreenHinder hinderUp = new HorizontalFullscreenHinder(VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER, 0, 0);
        HorizontalFullscreenHinder hinderDown = new HorizontalFullscreenHinder(VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER, 0, maxYInTrack);
        VerticalFullscreenHinder hinderRight = new VerticalFullscreenHinder(VisibleObjects.VERTICAL_FULLSCREEN_HINDER, maxXInTrack, edgeThicknes);
        VerticalFullscreenHinder hinderLeft = new VerticalFullscreenHinder(VisibleObjects.VERTICAL_FULLSCREEN_HINDER, 0, edgeThicknes);
    }

    private void setTrackBoundaries() {
        minXInTrack = edgeThicknes;
        maxXInTrack = GAME_WIDTH - edgeThicknes;
        minYInTrack = edgeThicknes;
        maxYInTrack = GAME_HEIGHT - edgeThicknes;
    }

    public static int getMinXInTrack() {
        return minXInTrack;
    }

    public static int getMaxXInTrack() {
        return maxXInTrack;
    }

    public static int getMinYInTrack() {
        return minYInTrack;
    }

    public static int getMaxYInTrack() {
        return maxYInTrack;
    }
    public static int getRandomXInTrack(int widthOfObject) {
        return minXInTrack + random.nextInt(maxXInTrack - minXInTrack - minXInTrack);
    }
    public static int getRandomYInTrack(int heightOfObject) {
        return minYInTrack + random.nextInt(maxYInTrack - minYInTrack - heightOfObject);
    }
}
