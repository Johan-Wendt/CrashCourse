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
    PLAYER_ONE("M0,31 L0,31 28,21 44,21 64,27 64,37 54,43 49,43 45,40 16,40 13,43 9,43 4,39 0,39 z", 64, 64),
    //HINDER("M 41.25,70.50", 40, 40);
    HINDER("M0 0 L0 40 40 40 40 0 Z", 40, 40);
    
    private final ArrayList<Image> images = new ArrayList<>();
    private final String SVGData;
    private final int width,  height;
    
    VisibleObjects(String SVGData, int width, int height) {
        this.SVGData = SVGData;
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
}
