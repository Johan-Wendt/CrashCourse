/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
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
                    Socket playerOneSocket = serverSocket.accept();
                    Socket playerTwoSocket = serverSocket.accept();
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


    
}
