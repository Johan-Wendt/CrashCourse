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
                ServerSocket serverSocket = new ServerSocket(SOCKET);
                
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
   // public Group getRoot() {
   //     return root;
  //  }
/**
    private void loadImages() {
        Image playerOne1 = new Image(getClass().getResourceAsStream("player-one1.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne2 = new Image(getClass().getResourceAsStream("player-one2.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne3 = new Image(getClass().getResourceAsStream("player-one3.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne4 = new Image(getClass().getResourceAsStream("player-one4.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne5 = new Image(getClass().getResourceAsStream("player-one5.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne6 = new Image(getClass().getResourceAsStream("player-one6.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne7 = new Image(getClass().getResourceAsStream("player-one7.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne1);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne2);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne3);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne4);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne5);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne6);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne7);
        
        Image playerTwo1 = new Image(getClass().getResourceAsStream("player-two1.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo2 = new Image(getClass().getResourceAsStream("player-two2.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo3 = new Image(getClass().getResourceAsStream("player-two3.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo4 = new Image(getClass().getResourceAsStream("player-two4.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo5 = new Image(getClass().getResourceAsStream("player-two5.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo6 = new Image(getClass().getResourceAsStream("player-two6.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo7 = new Image(getClass().getResourceAsStream("player-two7.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo1);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo2);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo3);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo4);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo5);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo6);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo7);
        Image hinder = new Image(getClass().getResourceAsStream("hinder.png"), VisibleObjects.SMALL_HINDER.getWidth(), VisibleObjects.SMALL_HINDER.getHeight(), true, false);
        VisibleObjects.SMALL_HINDER.getImages().add(hinder);
        Image Hhinder = new Image(getClass().getResourceAsStream("horizontal-hinder1.png"), VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getWidth(), VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getHeight(), false, false);
        VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getImages().add(Hhinder);
        Image Vhinder = new Image(getClass().getResourceAsStream("vertical-hinder.png"), VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getWidth(), VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getHeight(), false, false);
        VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getImages().add(Vhinder);
        Image makeFasterBonus = new Image(getClass().getResourceAsStream("fuelpump.png"), VisibleObjects.MAKE_FASTER_BONUS.getWidth(), VisibleObjects.MAKE_FASTER_BONUS.getHeight(), true, false);
        VisibleObjects.MAKE_FASTER_BONUS.getImages().add(makeFasterBonus);
        Image bomb = new Image(getClass().getResourceAsStream("bomb.png"), VisibleObjects.BOMB.getWidth(), VisibleObjects.BOMB.getHeight(), true, false);
        VisibleObjects.BOMB.getImages().add(bomb);
        Image explosion = new Image(getClass().getResourceAsStream("explosion4.gif"), VisibleObjects.EXPLOSION.getWidth(), VisibleObjects.EXPLOSION.getHeight(), true, false);
        VisibleObjects.EXPLOSION.getImages().add(explosion);
        Image crater = new Image(getClass().getResourceAsStream("crater.png"), VisibleObjects.CRATER.getWidth(), VisibleObjects.CRATER.getHeight(), true, false);
        VisibleObjects.CRATER.getImages().add(crater);
        Image lifeMeter = new Image(getClass().getResourceAsStream("lifeMeter.png"), VisibleObjects.LIFE_METER.getWidth(), VisibleObjects.LIFE_METER.getHeight(), true, false);
        VisibleObjects.LIFE_METER.getImages().add(lifeMeter);
        Image backGroundImage = new Image(getClass().getResourceAsStream("background.png"), GAME_WIDTH, GAME_HEIGHT, true, false);
        backGround = new ImageView(backGroundImage);
        Client.addToScreen(backGround);
    }
**/

    private void startGameLoop(Player playerOne, Player playerTwo,  Socket playerOneSocket, Socket playerTwoSocket) {
        gameLoop = new GameLoop(playerOne, playerTwo, playerOneSocket, playerTwoSocket);
        gameLoop.start();
    }


    
}
