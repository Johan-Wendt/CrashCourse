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
public class AudioHandler implements Constants{
    private AudioClip thud, squeek, crash, explosion;
    private AudioClip fuse;
    
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
        fuse.stop();
        explosion.play(0);
    }

    public void playSound(int sound, int Volume) {
        double soundVolume = Volume / 100.0;
        switch(sound) {
            case SOUND_THUD: if(!thud.isPlaying()) thud.play(soundVolume);
                break;
            case SOUND_SQUEEK: squeek.play(soundVolume);
                break;
            case SOUND_CRASH: crash.play(soundVolume);
                break;
            case SOUND_FUSE: if(!fuse.isPlaying()) fuse.play(soundVolume);
                break;
            case SOUND_STOP_FUSE: fuse.stop();
                break;
            case SOUND_EXPLOSION:
                explosion.play(soundVolume);
                fuse.stop();
                break;
        }
    }
}
