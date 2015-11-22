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
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = 600;
    private Player playerOne;
    private GameLoop gameLoop;
    private Scene scene;
    private TrackBuilder trackBuilder;
    
    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crash Course");
        primaryStage.show();
        createPopup();
        loadImages();
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
        Image playerOne = new Image(getClass().getResourceAsStream("playerOne.png"), VisibleObjects.PLAYER_ONE.getWidth(), VisibleObjects.PLAYER_ONE.getHeight(), true, false);
        VisibleObjects.PLAYER_ONE.getImages().add(playerOne);
        Image hinder = new Image(getClass().getResourceAsStream("hinder.png"), VisibleObjects.HINDER.getWidth(), VisibleObjects.HINDER.getHeight(), true, false);
        VisibleObjects.HINDER.getImages().add(hinder);
    }

    private void createPlayers() {
        //CrashCourse crashCourse, VisibleObjects deatils, Players playerDetails, int xLocation, int yLocation, int startSpeed
        playerOne = new Player(this, VisibleObjects.PLAYER_ONE, Players.PLAYER_ONE);
    }
    public Player getPlayer() {
        return playerOne;
    }

    private void startGameLoop() {
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    private void setPlayerStartControls() {
        playerOne.setControls(KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
    }

    private void createEventHandling() {
        scene.setOnKeyPressed(e -> {
            playerOne.takeKeyPressed(e.getCode());
        });
        scene.setOnKeyReleased(e -> {
            playerOne.takeKeyReleased(e.getCode());
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
    
}
