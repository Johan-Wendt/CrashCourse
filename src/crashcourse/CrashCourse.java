/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author johanwendt
 */
public class CrashCourse extends Application implements Constants{
    private MainPopup mainPopup;
    private GameLoop gameLoop;
    private static int currentGameSocket = SOCKET + 1;

    @Override
    public void start(Stage primaryStage) {
        
     //   createPopup();
        CollectableHandler.setProbabilityFactors();
        
        TextArea log = new TextArea();
        Scene scene = new Scene(new ScrollPane(log), 450, 200);
        primaryStage.setTitle("move rectangle");
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        
        primaryStage.show();
        
        new Thread( () -> {
            try {
                ServerSocket serverSocket= new ServerSocket(SOCKET);
                
                Platform.runLater(() -> log.appendText(new Date() + ": ServerSocket started at socket 8000\n"));

                
                while(true) {
                    Platform.runLater(() -> log.appendText(new Date() + "Waiting for player to join session " + '\n'));
                    Socket playerOneSockettemp = serverSocket.accept();
                    Socket playerOneSocket = givePlayerNewSocket(playerOneSockettemp);
                    Socket playerTwoSockettemp = serverSocket.accept();
                    Socket playerTwoSocket = givePlayerNewSocket(playerTwoSockettemp);
                    Platform.runLater(() -> log.appendText(new Date() + "Player joined session\n"));
                    Player playerOne = new Player(VisibleObjects.PLAYER_ONE, Players.PLAYER_ONE);
                    Player playerTwo = new Player(VisibleObjects.PLAYER_TWO, Players.PLAYER_TWO);

                    startGameLoop(playerOne, playerTwo, playerOneSocket, playerTwoSocket);
                }
            }   
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void createPopup() {
        //String title, String infoText, String okMessage, String cancelMessage, CrashCourse crashCourse
        mainPopup = new MainPopup("Crash Course", "Start Game", "Cancel", this);
    }
    private void startGameLoop(Player playerOne, Player playerTwo,  Socket playerOneSocket, Socket playerTwoSocket) {
        gameLoop = new GameLoop(playerOne, playerTwo, playerOneSocket, playerTwoSocket);
        gameLoop.start();
    }

    private Socket givePlayerNewSocket(Socket currentSocket) {
        try {
            ServerSocket serverSocket= new ServerSocket(currentGameSocket);
            DataOutputStream toPlayer = new DataOutputStream(currentSocket.getOutputStream());
            toPlayer.writeInt(currentGameSocket);
            currentGameSocket ++;
            Socket returnSocket = serverSocket.accept();
            return returnSocket;
        } 
        catch (IOException ex) {
            Logger.getLogger(CrashCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    
}
