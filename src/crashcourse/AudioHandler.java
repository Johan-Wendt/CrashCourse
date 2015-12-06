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
    private AudioClip thud, squeek, crash, fuse, explosion;
    
    public AudioHandler() {
        thud = new AudioClip(getClass().getResource("thud.wav").toExternalForm());
        squeek = new AudioClip(getClass().getResource("squeek.wav").toExternalForm());
        crash = new AudioClip(getClass().getResource("crash-hurt.wav").toExternalForm());
        fuse = new AudioClip(getClass().getResource("fuse.wav").toExternalForm());
        explosion = new AudioClip(getClass().getResource("explosion.wav").toExternalForm());
        
        thud.play(0);
        squeek.play(0);
        crash.play(0);
        fuse.play(0);
        explosion.play(0);
    }
    public void playThud() {
        if(!thud.isPlaying()) thud.play();
    }
    public void playThud(double volume) {
        if(!thud.isPlaying()) thud.play(volume);
    }
    public void playSqueek() {
        squeek.play();
    }
    public void playSqueek(double volume) {
        squeek.play(volume);
    }
    public void playCrash() {
        crash.play();
    }
    public void playCrash(double volume) {
        crash.play(volume);
    }
    public void playFuse() {
        fuse.play();
    }
    public void stopFuse() {
        fuse.stop();
    }
    public void playFuse(double volume) {
        fuse.play(volume);
    }
    public void playExplosion() {
        explosion.play();
    }
    public void playExplosion(double volume) {
        explosion.play(volume);
    }

}
