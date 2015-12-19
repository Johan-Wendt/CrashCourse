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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

/**
 *
 * @author johanwendt
 */
public class GameLoop extends AnimationTimer implements Constants {
    private Socket playerSocket;
    private DataInputStream fromPlayer;
    private DataOutputStream toPlayer;
    private Player playerOne;
    private Player playerTwo;
    private TrackBuilder trackBuilder;
    
    
    public GameLoop(Player playerOne, Player playerTwo, Socket playerSocket) {
        this.playerSocket = playerSocket;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
                
        createTrack();
        
        try {
            fromPlayer = new DataInputStream(playerSocket.getInputStream());
            toPlayer = new DataOutputStream(playerSocket.getOutputStream());

         //   toPlayer.write(1);
            
            } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void handle(long now) {
        takePlayerInput();
        
        HashSet<MovingObject> toMove = new HashSet<>(ObjectHandler.getCurrentMovingObjects());
        for(MovingObject object: toMove) {
            object.act();
        }
        CollectableHandler.act();
        addNewObjectsToClients();
        removeOldObjectsFromClient();
        sendPositionsToClient();
        playSounds();
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
        super.stop();
    }

    private void takePlayerInput() {
        try {
            if(fromPlayer.available() > 0) {

                int direction = fromPlayer.read();
                if(direction != -1) {
                    playerOne.takeTurn(direction);
                }
            } 
        }
        catch (IOException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addNewObjectsToClients() {
        HashSet<VisibleObject> toAdd = new HashSet<>(ObjectHandler.getObjectsToAddToClient());
        for(VisibleObject newObject : toAdd) {
            try {
                toPlayer.writeInt(ACTION_CREATE_NEW);
                toPlayer.writeInt(newObject.getObjectNumber());
                toPlayer.writeInt(newObject.getImageNumber());
                
                if(newObject instanceof LifeMeter) {
                    LifeMeter lifeMeter = (LifeMeter) newObject;
                    toPlayer.writeInt(lifeMeter.getLifeFactor());
                    toPlayer.writeInt(lifeMeter.getMeterXLocation());
                    toPlayer.writeInt(lifeMeter.getMeterYLocation());
                    toPlayer.writeInt(lifeMeter.getMeterWidth());
                    toPlayer.writeInt(lifeMeter.getMeterHeight());
                }
                
            } catch (IOException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObjectHandler.clearAddClient();
    }

    private void removeOldObjectsFromClient() {
        HashSet<VisibleObject> toRemove = new HashSet<>(ObjectHandler.getObjectsToRemoveFromClient());
        for(VisibleObject oldObject : toRemove) {
            try {
                toPlayer.writeInt(ACTION_DESTROY_OLD);
                toPlayer.writeInt(oldObject.getObjectNumber());
            } catch (IOException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObjectHandler.clearRemoveClient();
    }

    private void sendPositionsToClient() {
        HashSet<VisibleObject> toMove = new HashSet<>(ObjectHandler.getCurrentObjects());
        for(VisibleObject object : toMove) {
            try {
                toPlayer.writeInt(ACTION_SET_POSITION);
                toPlayer.writeInt(object.getObjectNumber());
                toPlayer.writeInt((int) object.getxLocation());
                toPlayer.writeInt((int) object.getyLocation());
                toPlayer.writeInt((int) object.getFacingRotation());
                
                if(object.isChangedAppearance()) {
                    toPlayer.writeInt(ACTION_CHANGE_APPEARANCE);
                    toPlayer.writeInt(object.getObjectNumber());
                    toPlayer.writeInt(object.getImageNumber());
                    object.setChangedAppearance(false);
                }
            } 
            catch (IOException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void playSounds() {
        HashSet<VisibleObject> soundPlayers = new HashSet<>(ObjectHandler.getCurrentObjects());
        for(VisibleObject object : soundPlayers) {
            if(object.getPlayAudio() >= 0) {
                try {
                    toPlayer.writeInt(ACTION_PLAY_SOUND);
                    toPlayer.writeInt(object.getPlayAudio());
                    toPlayer.writeInt(object.getAudioVolume());
                    object.setPlayAudio(-1);
                }
                catch (IOException ex) {
                    Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
    }
    
        private void createTrack() {
        trackBuilder = new TrackBuilder();
        trackBuilder.buildStandardTrack();
    }
}
