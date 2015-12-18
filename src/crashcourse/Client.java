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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author johanwendt
 */
public class Client extends Application implements Constants{
    private Scene scene;
    private static Group root = new Group();
    private Player playerOne, playerTwo;
    private TrackBuilder trackBuilder;
    
    private DataInputStream fromServer;
    private DataOutputStream toServer;


    @Override
    public void start(Stage primaryStage) {
        connectToServer();
        setTheStage(primaryStage);
        createTrack();
        createPlayers();
        createEventHandling();
        setPlayerStartControls();
        
    }
    
    private void setTheStage(Stage primaryStage) {
        scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crash Course");
        primaryStage.show();
        
        
    }
    public static void removeFromScreen(Node toRemove) {
        root.getChildren().remove(toRemove);
    }
    public static void addToScreen(Node toAdd) {
        root.getChildren().add(toAdd);
    }
    private void createEventHandling() {
        scene.setOnKeyPressed(e -> {
            playerOne.takeKeyPressed(e.getCode());
            playerTwo.takeKeyPressed(e.getCode());
            if(e.getCode().equals(KeyCode.R)) {
         //       reStart();
            }
        });
        scene.setOnKeyReleased(e -> {
            playerOne.takeKeyReleased(e.getCode());
            playerTwo.takeKeyReleased(e.getCode());
        });
    }
    private void createTrack() {
        trackBuilder = new TrackBuilder();
        trackBuilder.buildStandardTrack();
    }
    private void reStart() {
        ObjectHandler.resetAllObjects();
        trackBuilder.buildStandardTrack();
        createPlayers();
        setPlayerStartControls();
    }
    private void createPlayers() {
        //CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, int xLocation, int yLocation, int startSpeed
        playerOne = new Player(VisibleObjects.PLAYER_ONE, Players.PLAYER_ONE);
        playerTwo = new Player(VisibleObjects.PLAYER_TWO, Players.PLAYER_TWO);
    }
    private void setPlayerStartControls() {
        playerOne.setControls(KeyCode.UP, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.DOWN);
        playerTwo.setControls(KeyCode.W, KeyCode.D, KeyCode.A, KeyCode.S);
    }
    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", SOCKET);
            
            fromServer = new DataInputStream(socket.getInputStream());
            
            toServer = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    
    /**
        new Thread(() -> {
            while(true) {
                
                try {
                    int apa = fromServer.readInt();
                    System.out.println("From server " + apa);
                    setPostion(apa);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    changeDirection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }).start();
        **/
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
}
