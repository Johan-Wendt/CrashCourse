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
public enum VisibleObjects {
    PLAYER_ONE ("M 2.21,3.64 C 2.21,3.64 6.07,0.29 6.07,0.29 6.07,0.29 13.64,0.36 13.64,0.36 13.64,0.36 17.71,3.64 17.71,3.64 17.71,3.64 17.79,37.79 17.79,37.79 17.79,37.79 13.79,39.64 13.79,39.64 13.79,39.64 5.93,39.64 5.93,39.64 5.93,39.64 2.00,37.79 2.00,37.79 Z","M4,0 H 10 V 40 V 0 H 16", "", "", "", 20, 40, 0.2, 0.4),
    PLAYER_TWO ("M 2.21,3.64 C 2.21,3.64 6.07,0.29 6.07,0.29 6.07,0.29 13.64,0.36 13.64,0.36 13.64,0.36 17.71,3.64 17.71,3.64 17.71,3.64 17.79,37.79 17.79,37.79 17.79,37.79 13.79,39.64 13.79,39.64 13.79,39.64 5.93,39.64 5.93,39.64 5.93,39.64 2.00,37.79 2.00,37.79 Z","M4,0 H 10 V 40 V 0 H 16", "", "", "", 20, 40, 0.2, 0.4),
   //  PLAYER_ONE("M 2.21,3.64 C 2.21,3.64 6.07,0.29 6.07,0.29 6.07,0.29 13.64,0.36 13.64,0.36 13.64,0.36 17.71,3.64 17.71,3.64 17.71,3.64 17.79,37.79 17.79,37.79 17.79,37.79 13.79,39.64 13.79,39.64 13.79,39.64 5.93,39.64 5.93,39.64 5.93,39.64 2.00,37.79 2.00,37.79 Z","M4,0 H 10 V 40 V 0 H 16", "M18,0 V 40", "M2,40 H 18", "M0,2 V 40", 20, 40, (float) 0.1),
   // PLAYER_TWO("M 2.21,3.64 C 2.21,3.64 6.07,0.29 6.07,0.29 6.07,0.29 13.64,0.36 13.64,0.36 13.64,0.36 17.71,3.64 17.71,3.64 17.71,3.64 17.79,37.79 17.79,37.79 17.79,37.79 13.79,39.64 13.79,39.64 13.79,39.64 5.93,39.64 5.93,39.64 5.93,39.64 2.00,37.79 2.00,37.79 Z","M4,0 H 10 V 40 V 0 H 16", "M18,0 V 40", "M2,40 H 18", "M0,2 V 40", 20, 40, (float) 0.1),
   
    SMALL_HINDER("M0,0 L0,40 40,40 40,0 Z", "M0,0 H 40", "M40,0 V 40", "M0,40 H 40", "M0,0 V 40", 40, 40, 0.3, 0.4),
    HORIZONTAL_FULLSCREEN_HINDER ("M0,0 L0,40 1200,40 1200,0 Z", "M0,0 H 1200", "M1200,0 V 40", "M0,40 H 1200", "M0,0 V 40", 1200, 80, 0.3, 0.4),
   // VERTICAL_FULLSCREEN_HINDER("M0,40 L0,560 40,560 40,40 Z", "M0,40 H 40", "M40,40 V 560", "M0,560 H 40", "M0,40 V 560", 40, 520);
    VERTICAL_FULLSCREEN_HINDER ("M0,0 L0,660 40,660 40,0 Z", "M0,0 H 40", "M40,0 V 620", "M0,620 H 40", "M0,0 V 660", 80, 620, 0.3, 0.4),
    MAKE_FASTER_BONUS ("M0,0 L0,20 20,20 20,0 Z", "", "", "", "", 20, 20, 0.3, 0.4),
    BOMB ("M0,0 L0,16 30,16 30,0 Z", "M0,0 H 30", "M30,0 V 16", "M0,16 H30", "M0,0 V16", 30, 16, 0.4, 0.4),
    EXPLOSION ("M0,0 L0,138 200,138 200,0 Z", "M0,0 H 200", "M200,0 V 138", "M0,138 H200", "M0,0 V138", 200, 138, 0.8, 0.4),
    CRATER ("M0,0 L0,27 30,27 30,0 Z", "M0,0 H 30", "M30,0 V 27", "M0,27 H30", "M0,0 27", 30, 27, 0.3, 0.4),
    LIFE_METER ("0", "0", "0", "0", "0", 12, 64, 0, 0);
    
    private final ArrayList<Image> images = new ArrayList<>();
    private final String SVGData;
    private final String SVGDataUpp;
    private final String SVGDataRight;
    private final String SVGDataDown;
    private final String SVGDataLeft;
    private final int width, height;
    private final double bounciness, weightFactor;
    
    VisibleObjects(String SVGData, String SVGDataUpp, String SVGDataRight, String SVGDataDown, String SVGDataLeft, int width, int height, double bounciness, double weightFactor) {
        this.SVGData = SVGData;
        this.SVGDataUpp = SVGDataUpp;
        this.SVGDataRight = SVGDataRight;
        this.SVGDataDown = SVGDataDown;
        this.SVGDataLeft = SVGDataLeft;
        this.width = width;
        this.height = height;
        this.bounciness = bounciness;
        this.weightFactor = weightFactor;
    }
    public ArrayList<Image> getImages() {
        return images;
    }
    public String getSVGData() {
        return SVGData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getSVGDataUpp() {
        return SVGDataUpp;
    }

    public String getSVGDataRight() {
        return SVGDataRight;
    }

    public String getSVGDataDown() {
        return SVGDataDown;
    }

    public String getSVGDataLeft() {
        return SVGDataLeft;
    }
    public double getBounciness() {
        return bounciness;
    }

    public double getWeightFactor() {
        return weightFactor;
    }
    
}
