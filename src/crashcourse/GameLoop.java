/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import javafx.animation.AnimationTimer;

/**
 *
 * @author johanwendt
 */
public class GameLoop extends AnimationTimer {
    private Socket playerSocket;
    private DataInputStream fromPlayer;
    private DataOutputStream toPlayer;
    
    
    public GameLoop(Socket playerSocket) {
        this.playerSocket = playerSocket;
        
        try {
            fromPlayer = new DataInputStream(playerSocket.getInputStream());
            toPlayer = new DataOutputStream(playerSocket.getOutputStream());

            toPlayer.write(1);
            
            } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void handle(long now) {
        HashSet<MovingObject> toMove = new HashSet<>(ObjectHandler.getCurrentMovingObjects());
        for(MovingObject  object: toMove) {
            object.act();
        }
        CollectableHandler.act();
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
        super.stop();
    }
}
