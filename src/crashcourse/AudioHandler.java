/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.scene.media.AudioClip;

/**
 *
 * @author johanwendt
 */
public class AudioHandler {
    private static AudioClip thud, squeek;
    
    public AudioHandler() {
        thud = new AudioClip(getClass().getResource("thud.wav").toExternalForm());
        squeek = new AudioClip(getClass().getResource("squeek.wav").toExternalForm());
    }
    public static void playThud() {
        thud.play();
    }
    public static void playThud(double volume) {
        thud.play(volume);
    }
    public static void playSqueek() {
        squeek.play();
    }
    public static void playSqueek(double volume) {
        squeek.play(volume);
    }

}
