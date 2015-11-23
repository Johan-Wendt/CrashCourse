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
    PLAYER_ONE("M0,31 L0,31 28,21 44,21 64,27 64,37 54,43 49,43 45,40 16,40 13,43 9,43 4,39 0,39 z","", "", "", "", 64, 64),
    //HINDER("M 41.25,70.50", 40, 40);
    HINDER("M0 0 L0 40 40 40 40 0 Z", "M0 0 H 40", "M40 0 V 40", "M0 40 H 40", "M0 0 V 40", 40, 40);
    
    private final ArrayList<Image> images = new ArrayList<>();
    private final String SVGData;
    private final String SVGDataUpp;
    private final String SVGDataRight;
    private final String SVGDataDown;
    private final String SVGDataLeft;
    private final int width,  height;
    
    VisibleObjects(String SVGData, String SVGDataUpp, String SVGDataRight, String SVGDataDown, String SVGDataLeft, int width, int height) {
        this.SVGData = SVGData;
        this.SVGDataUpp = SVGDataUpp;
        this.SVGDataRight = SVGDataRight;
        this.SVGDataDown = SVGDataDown;
        this.SVGDataLeft = SVGDataLeft;
        this.width = width;
        this.height = height;
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
    
}
