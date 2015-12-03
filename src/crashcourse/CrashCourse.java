/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author johanwendt
 */
public class CrashCourse extends Application {
    private MainPopup mainPopup;
    private Group root = new Group();
    private Player playerOne, playerTwo;
    private GameLoop gameLoop;
    private Scene scene;
    private TrackBuilder trackBuilder;
    
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 700;
    
    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crash Course");
        primaryStage.show();
        createPopup();
        loadImages();
        AudioHandler audioHandler = new AudioHandler();
        createPlayers();
        createTrack();
        setPlayerStartControls();
        createEventHandling();
        startGameLoop();
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
    public Group getRoot() {
        return root;
    }

    private void loadImages() {
        Image playerOne1 = new Image(getClass().getResourceAsStream("player-one1.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne2 = new Image(getClass().getResourceAsStream("player-one2.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne3 = new Image(getClass().getResourceAsStream("player-one3.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne4 = new Image(getClass().getResourceAsStream("player-one4.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        Image playerOne5 = new Image(getClass().getResourceAsStream("player-one5.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne1);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne2);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne3);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne4);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne5);
        Image playerTwo1 = new Image(getClass().getResourceAsStream("player-two1.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo2 = new Image(getClass().getResourceAsStream("player-two2.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo3 = new Image(getClass().getResourceAsStream("player-two3.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo4 = new Image(getClass().getResourceAsStream("player-two4.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        Image playerTwo5 = new Image(getClass().getResourceAsStream("player-two5.png"), VisibleObjects.PLAYER_TWO.getWidth(), VisibleObjects.PLAYER_TWO.getHeight(), true, false);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo1);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo2);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo3);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo4);
        VisibleObjects.PLAYER_TWO.getImages().add(playerTwo5);
        Image hinder = new Image(getClass().getResourceAsStream("hinder.png"), VisibleObjects.SMALL_HINDER.getWidth(), VisibleObjects.SMALL_HINDER.getHeight(), true, false);
        VisibleObjects.SMALL_HINDER.getImages().add(hinder);
        Image Hhinder = new Image(getClass().getResourceAsStream("horizontal-hinder.png"), VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getWidth(), VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getHeight(), false, false);
        VisibleObjects.HORIZONTAL_FULLSCREEN_HINDER.getImages().add(Hhinder);
        Image Vhinder = new Image(getClass().getResourceAsStream("vertical-hinder.png"), VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getWidth(), VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getHeight(), false, false);
        VisibleObjects.VERTICAL_FULLSCREEN_HINDER.getImages().add(Vhinder);
    }

    private void createPlayers() {
        //CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, int xLocation, int yLocation, int startSpeed
        playerOne = new Player(this, VisibleObjects.PLAYER_ONE, Players.PLAYER_ONE);
        playerTwo = new Player(this, VisibleObjects.PLAYER_TWO, Players.PLAYER_TWO);
    }
    public Player getPlayerOne() {
        return playerOne;
    }
    public Player getPlayerTwo() {
        return playerTwo;
    }

    private void startGameLoop() {
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    
    private void setPlayerStartControls() {
        playerOne.setControls(KeyCode.UP, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.DOWN);
        playerTwo.setControls(KeyCode.W, KeyCode.D, KeyCode.A, KeyCode.S);
    }
    

    private void createEventHandling() {
        scene.setOnKeyPressed(e -> {
            playerOne.takeKeyPressed(e.getCode());
            playerTwo.takeKeyPressed(e.getCode());
            if(e.getCode().equals(KeyCode.R)) {
                reStart();
            }
        });
        scene.setOnKeyReleased(e -> {
            playerOne.takeKeyReleased(e.getCode());
            playerTwo.takeKeyReleased(e.getCode());
        });
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    private void createTrack() {
        trackBuilder = new TrackBuilder(this);
        trackBuilder.buildStandardTrack();
    }
    private void reStart() {
        playerOne.removePlayer(this);
        playerTwo.removePlayer(this);
        createPlayers();
        setPlayerStartControls();
    }
    public void removeFromScreen(Node toRemove) {
        root.getChildren().remove(toRemove);
    }
    
}
