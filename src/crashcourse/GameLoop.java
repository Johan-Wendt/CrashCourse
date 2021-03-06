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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

/**
 *
 * @author johanwendt
 */
public class GameLoop extends AnimationTimer implements Constants {
    private Socket playerOneSocket, playerTwoSocket;
    private DataInputStream fromPlayerOne, fromPlayerTwo;
    private DataOutputStream toPlayerOne, toPlayerTwo;
    private Player playerOne;
    private Player playerTwo;
    private TrackBuilder trackBuilder;
    private ArrayList<byte[]> sendList = new ArrayList<>();
    
    
    public GameLoop(Player playerOne, Player playerTwo, Socket playerOneSocket, Socket playerTwoSocket) {
        this.playerOneSocket = playerOneSocket;
        this.playerTwoSocket = playerTwoSocket;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
                
        createTrack();
        
        try {
            fromPlayerOne = new DataInputStream(playerOneSocket.getInputStream());
            toPlayerOne = new DataOutputStream(playerOneSocket.getOutputStream());
            
            fromPlayerTwo = new DataInputStream(playerTwoSocket.getInputStream());
            toPlayerTwo = new DataOutputStream(playerTwoSocket.getOutputStream());
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
        sendListToPlayers();
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
            if(fromPlayerOne.available() > 0) {
                int direction = fromPlayerOne.read();
                if(direction != -1) {
                    if(direction == PLAYER_INPUT_RESTART) restart();
                    else playerOne.takeTurn(direction);
                }
            } 
            if(fromPlayerTwo.available() > 0) {
                int direction = fromPlayerTwo.read();
                if(direction != -1) {
                    if(direction == PLAYER_INPUT_RESTART) restart();
                    else playerTwo.takeTurn(direction);
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
            byte[] creator = new byte[18];
            creator[0] = ACTION_CREATE_NEW;
            byte[] objectNumber = intToByteArray(newObject.getObjectNumber());
            for(int k = 0; k < 4; k++) {
                    creator[k + 1] = objectNumber[k];
                }
            byte[] xLocation = intToByteArray((int) newObject.getxLocation());
            byte[] yLocation = intToByteArray((int) newObject.getyLocation());
            byte[] rotation = intToByteArray((int) newObject.getFacingRotation());
            
            for(int k = 0; k < 4; k++) {
                    creator[k + 5] = xLocation[k];
                }
            for(int k = 0; k < 4; k++) {
                creator[k + 9] = yLocation[k];
            }
            for(int k = 0; k < 4; k++) {
                creator[k + 13] = rotation[k];
            }
            
            creator[17] = (byte) newObject.getImageNumber();
            //writeToPlayers(creator);
            
            sendList.add(creator);

            if(newObject instanceof LifeMeter) {
                LifeMeter lifeMeter = (LifeMeter) newObject;
                byte[] life = new byte[11];
                byte[] xMeterLocation = intToByteArray(lifeMeter.getMeterXLocation());
                byte[] yMeterLocation = intToByteArray(lifeMeter.getMeterYLocation());

                life[0] = (byte) lifeMeter.getLifeFactor();
                for(int k = 0; k < 4; k++) {
                    life[k + 1] = xMeterLocation[k];
                }
                for(int k = 0; k < 4; k++) {
                    life[k + 5] = yMeterLocation[k];
                }
                life[9] = (byte) lifeMeter.getMeterWidth();
                life[10] = (byte) lifeMeter.getMeterHeight();
              //  writeToPlayers(life);
                sendList.add(life);
            } 
        }
        ObjectHandler.clearAddClient();
    }

    private void removeOldObjectsFromClient() {
        HashSet<VisibleObject> toRemove = new HashSet<>(ObjectHandler.getObjectsToRemoveFromClient());
        for(VisibleObject oldObject : toRemove) {
            byte[] destroyer = new byte[5];
            destroyer[0] = ACTION_DESTROY_OLD;
            byte[] objectNumber = intToByteArray(oldObject.getObjectNumber());
            for(int k = 0; k < 4; k++) {
                    destroyer[k + 1] = objectNumber[k];
                }
            //writeToPlayers(destroyer);
            sendList.add(destroyer);
        }
        ObjectHandler.clearRemoveClient();
    }

    private void sendPositionsToClient() {
        HashSet<MovingObject> toMove = new HashSet<>(ObjectHandler.getCurrentMovingObjects());
        for(MovingObject object : toMove) {
            byte[] newLocation = new byte[17];
            newLocation[0] = ACTION_SET_POSITION;
            byte[] objectNumber = intToByteArray(object.getObjectNumber());
            byte[] xLocation = intToByteArray((int) object.getxLocation());
            byte[] yLocation = intToByteArray((int) object.getyLocation());
            byte[] rotation = intToByteArray((int) object.getFacingRotation());

            for(int k = 0; k < 4; k++) {
                newLocation[k + 1] = objectNumber[k];
            }
            for(int k = 0; k < 4; k++) {
                newLocation[k + 5] = xLocation[k];
            }
            for(int k = 0; k < 4; k++) {
                newLocation[k + 9] = yLocation[k];
            }
            for(int k = 0; k < 4; k++) {
                newLocation[k + 13] = rotation[k];
            }

            //writeToPlayers(newLocation);
            sendList.add(newLocation);

            if(object.isChangedAppearance()) {
                byte[] newAppearance = new byte[6];
                newAppearance[0] = ACTION_CHANGE_APPEARANCE;
                byte[] objectNumberAppearance = intToByteArray(object.getObjectNumber());
                for(int k = 0; k < 4; k++) {
                    newAppearance[k + 1] = objectNumberAppearance[k];
                }
                newAppearance[5] = (byte) object.getImageNumber();
             //   writeToPlayers(newAppearance);
                
                sendList.add(newAppearance);
                object.setChangedAppearance(false);
            }
        }
    }

    private void playSounds() {
        HashSet<VisibleObject> soundPlayers = new HashSet<>(ObjectHandler.getCurrentObjects());
        for(VisibleObject object : soundPlayers) {
            if(object.getPlayAudio() >= 0) {
                byte[] sound = new byte[3];
                sound[0] = ACTION_PLAY_SOUND;
                sound[1] = (byte) object.getPlayAudio();
                sound[2] = (byte) object.getAudioVolume();
               // writeToPlayers(sound);
                
                sendList.add(sound);
                object.setPlayAudio(-1);
            }
        }
    }
    
        private void createTrack() {
        trackBuilder = new TrackBuilder();
        trackBuilder.buildStandardTrack();
    }
    private byte[] intToByteArray(int value) {
        return new byte[] {
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value};
    } 
    private void writeToPlayers(byte[] info) {
        try {
            toPlayerOne.write(info);
            toPlayerTwo.write(info);
        } catch (IOException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void sendListToPlayers() {
        byte[] toSend = buildByteSendList();
        writeToPlayers(toSend);
        sendList.clear();
    }

    private byte[] buildByteSendList() {
        int size = 0;
        for(byte[] list : sendList) {
            size += list.length;
        }
        byte [] byteSendList = new byte[size];
        int n = 0;
        for(byte[] list : sendList) {
            for(byte toSend : list) {
                byteSendList[n] = toSend;
                n++;
            }
        }
        return byteSendList;
    }
    private void restart() {
        sendRestartToClients();
        ObjectHandler.resetAllObjects();
        trackBuilder.buildStandardTrack();
        reCreatePlayers();
    }
    private void reCreatePlayers() {
        playerOne = new Player(VisibleObjects.PLAYER_ONE, Players.PLAYER_ONE);
        playerTwo = new Player(VisibleObjects.PLAYER_TWO, Players.PLAYER_TWO);
    }

    private void sendRestartToClients() {
        HashSet<VisibleObject> toRemove = new HashSet<>(ObjectHandler.getCurrentObjects());
        for(VisibleObject oldObject : toRemove) {
            byte[] destroyer = new byte[5];
            destroyer[0] = ACTION_DESTROY_OLD;
            byte[] objectNumber = intToByteArray(oldObject.getObjectNumber());
            for(int k = 0; k < 4; k++) {
                    destroyer[k + 1] = objectNumber[k];
                }
            //writeToPlayers(destroyer);
            writeToPlayers(destroyer);
        }
        byte[] noSound = new byte[2];
        noSound[0] = ACTION_PLAY_SOUND;
        noSound[1] = SOUND_STOP_FUSE;
    }
}
