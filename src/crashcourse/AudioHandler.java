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
    private static AudioClip thud, squeek, crash;
    
    public AudioHandler() {
        thud = new AudioClip(getClass().getResource("thud.wav").toExternalForm());
        squeek = new AudioClip(getClass().getResource("squeek.wav").toExternalForm());
        crash = new AudioClip(getClass().getResource("crash-hurt.wav").toExternalForm());
        
        
        thud.play(0);
        squeek.play(0);
        crash.play(0);
    }
    public static void playThud() {
        if(!thud.isPlaying()) thud.play();
    }
    public static void playThud(double volume) {
        if(!thud.isPlaying()) thud.play(volume);
    }
    public static void playSqueek() {
        if(!squeek.isPlaying()) squeek.play();
    }
    public static void playSqueek(double volume) {
        if(!squeek.isPlaying()) squeek.play(volume);
    }
    public static void playCrash() {
        if(!crash.isPlaying()) crash.play();
    }
    public static void playCrash(double volume) {
        if(!crash.isPlaying()) crash.play(volume);
    }

}
