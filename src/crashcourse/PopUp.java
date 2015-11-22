/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *Class that is the base for all popup, that is all the stages except for the
 * main stage where the game is played.
 * @author johanwendt
 */
public abstract class PopUp {
    
    private final Stage pupUpStage = new Stage();
    private Scene popUpScene;
    private final Label infoLabelText = new Label();
  //  private Label extraLabel = new Label();
    private final Button okButton = new Button();
    private final Button cancelButton = new Button();
    
    
    private final VBox popUpPane = new VBox();
    private final HBox popUpPaneInnerUpper = new HBox();
    private final HBox popUpPaneInnerLower = new HBox();
    private Group popUpPaneInnerMiddle = new Group();
    
    private final static int STANDARD_PANE_WIDTH = 600;
    private final static int STANDARD_PANE_HEIGHT = 600;
    private final static int INNER_UPPER_HEIGHT = STANDARD_PANE_HEIGHT - 50;
    private final static int INNER_LOWER_HEIGHT = STANDARD_PANE_HEIGHT - INNER_UPPER_HEIGHT;
    private final static int STANDARD_PADDING = 20;
    
    protected final static int STANDARD_BUTTON_HEIGHT = 70;
    
    private Background background;
    
    /**
     * Create a popup with a title, a message for the ok button and a preffered 
     * width for the pane.
     * @param title title of the stage.
     * @param okMessage message displayed on the confirmation button.
     * @param paneWidth prefered width of the pane
     */
    public PopUp() {
        setUpOuterPart();
        setUpLowerPart();
        setUpMiddlePane();
        setUpUpperPart();
    }
    public PopUp(String title, String okMessage, String cancelMessage) {
        setUpOuterPart(title, STANDARD_PANE_WIDTH);
        setUpLowerPart();
        setUpMiddlePane();
        setUpUpperPart();
        addOkButton(okMessage);
        addCancelButton(cancelMessage);
        setStandardOnStageKeyBehaviour();
        setOnActions();
        okButton.requestFocus();
    }
    /**
    public PopUp(String title, String okMessage, String cancelMessage, int paneWidth) {
        setUpOuterPart(title, paneWidth);
        setUpLowerPart();
        setUpMiddlePane();
        setUpUpperPart();
        addOkButton(okMessage);
        addCancelButton(cancelMessage);
        setStandardOnStageKeyBehaviour();
        setOnActions();
        okButton.requestFocus();
    }
    public PopUp(String title, String infoText, String okMessage, String cancelMessage, int paneWidth) {
        setUpOuterPart(title, paneWidth);
        setUpLowerPart();
        setUpMiddlePane();
        setUpUpperPart();
        setUpInfoLabel(infoText);
        addOkButton(okMessage);
        addCancelButton(cancelMessage);
        setStandardOnStageKeyBehaviour();
        setOnActions();
        okButton.requestFocus();
    }
    * 
    * **/
    private void setUpOuterPart() {
        pupUpStage.setAlwaysOnTop(true);
   //     pupUpStage.initOwner(UserInterface.getBattleStage());
        pupUpStage.setOnCloseRequest(c -> {
            showPopUp(false);
        });
        popUpPane.getChildren().addAll(popUpPaneInnerUpper, popUpPaneInnerMiddle, popUpPaneInnerLower);
        popUpScene = new Scene(popUpPane);
        pupUpStage.setScene(popUpScene);
        pupUpStage.setResizable(false);
    }
    private void setUpOuterPart(String title, int paneWidth) {
        pupUpStage.setTitle(title);
        pupUpStage.setAlwaysOnTop(true);
   //     pupUpStage.initOwner(UserInterface.getBattleStage());
        pupUpStage.setOnCloseRequest(c -> {
            showPopUp(false);
        });
      //  setBackGround("test.jpg", true);
                
        popUpPane.getChildren().addAll(popUpPaneInnerUpper, popUpPaneInnerMiddle, popUpPaneInnerLower);
        
        popUpScene = new Scene(popUpPane, paneWidth, STANDARD_PANE_HEIGHT);
        pupUpStage.setScene(popUpScene);
        pupUpStage.setResizable(false);
    }
    private void setUpUpperPart() { 
        popUpPaneInnerUpper.setPadding(new Insets(STANDARD_PADDING / 2, STANDARD_PADDING, STANDARD_PADDING / 4, STANDARD_PADDING));
        popUpPaneInnerUpper.setPrefHeight(INNER_UPPER_HEIGHT);
    }
    private void setUpMiddlePane() {
       // popUpPane.getChildren().add(1, popUpPaneInnerMiddle);
        //popUpPaneInnerMiddle.setAlignment(Pos.CENTER);
      //  popUpPaneInnerMiddle.getChildren().add(extraLabel);
    }
    private void setUpLowerPart() {
        popUpPaneInnerLower.setPrefHeight(INNER_LOWER_HEIGHT);
        popUpPaneInnerLower.setAlignment(Pos.CENTER_RIGHT);
        popUpPaneInnerLower.setPadding(new Insets(STANDARD_PADDING));
        HBox.setMargin(okButton, new Insets(0, STANDARD_PADDING / 4, 0, STANDARD_PADDING / 4));

    }
    
    protected void showPopUp(boolean show) {
        if(show && !pupUpStage.isShowing()) {
            pupUpStage.show();
   //         GameEngine.popUpOpened();
        }
        if(!show && pupUpStage.isShowing()) {
            pupUpStage.hide();
   //         GameEngine.popUpClosed();
        }
    }
    protected void addOkButton(String okMessage) {
        okButton.setText(okMessage);
        okButton.setPrefWidth(okMessage.length() * 9);
        okButton.setStyle("-fx-base: #009933;");
        okButton.setPrefHeight(STANDARD_BUTTON_HEIGHT);
        popUpPaneInnerLower.getChildren().add(getOkButton());
    }
    protected void addCancelButton(String cancelMessage) { 
        cancelButton.setText(cancelMessage);
        cancelButton.setCancelButton(true);
        cancelButton.setPrefWidth(cancelMessage.length() * 9);
        cancelButton.setStyle("-fx-base: #FF3300;");
        cancelButton.setPrefHeight(STANDARD_BUTTON_HEIGHT);
        popUpPaneInnerLower.getChildren().add(getCancelButton());
    }
    protected void addExtraButton(Control extraButton) {
        extraButton.setStyle("-fx-base: #0000FF; -fx-font-size: 15px;");
        extraButton.setPrefHeight(STANDARD_BUTTON_HEIGHT);
        popUpPaneInnerLower.getChildren().add(0, extraButton);
    }
    protected void addExtraButton(Control extraButton, int StringLength) {
        extraButton.setPrefWidth(StringLength * 9);
        extraButton.setStyle("-fx-base: #0000FF; -fx-font-size: 15px;");
        extraButton.setPrefHeight(STANDARD_BUTTON_HEIGHT);
        popUpPaneInnerLower.getChildren().add(0, extraButton);
    }

    protected Button getOkButton() {
        return okButton;
    }
    protected Button getCancelButton() {
        return cancelButton;
    }
    public VBox getPopUpPane() {
        return popUpPane;
    }
    protected HBox getPopUpPaneInnerUpper() {
        return popUpPaneInnerUpper;
    }
    protected HBox getPopUpPaneInnerLower() {
        return popUpPaneInnerLower;
    }
    protected void setStandardOnStageKeyBehaviour() {
        okButton.defaultButtonProperty().bind(okButton.focusedProperty());
        cancelButton.defaultButtonProperty().bind(cancelButton.focusedProperty());
    }
    /**
     *Set actions for pressing the buttons. The canelButton usually just closes 
     * the popup while the okButton closes the popup and performs some
     * action.
     */
    protected abstract void setOnActions();
    
    /**
    protected void addInfoPicture(ImageView picture) {
        extraLabel.setGraphic(picture);
    }
    protected Label getExtraLabel() {
        return extraLabel;
    }
    * **/
    protected void setBackGround(String name, boolean cover) {
        Image image = new Image(getClass().getResourceAsStream(name));
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, cover);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        background = new Background(backgroundImage);
      //  popUpPane.setBackground(background);
    }
    /**
    protected void setMiddleContent(Node node) {
        popUpPaneInnerMiddle.getChildren().add(node);
       // popUpPaneInnerUpper.setAlignment(Pos.TOP_CENTER);
    }
    * **/
    protected Stage getPopUpStage() {
        return pupUpStage;
    }
    protected static int getStandardPadding() {
        return STANDARD_PADDING;
    }
    protected void addMenuChoice(String shortCutName, Pane menuChoice) {
        final Button shortCutButton = new Button(shortCutName);
        shortCutButton.setStyle("-fx-base: #01c9f3; -fx-font-size: 15px;");
        shortCutButton.setMinWidth(150);
        shortCutButton.setPadding(new Insets(PopUp.getStandardPadding() * 3));
        shortCutButton.setMinWidth(100);
        
        menuChoice.setPrefSize(STANDARD_PANE_WIDTH, STANDARD_PANE_HEIGHT - 200);
        
        popUpPaneInnerUpper.getChildren().add(shortCutButton);
        popUpPaneInnerMiddle.getChildren().add(menuChoice);
        
        shortCutButton.setOnAction(e -> {
            menuChoice.toFront();
        });
        if(background != null) {
            menuChoice.setBackground(background);
        }
    }
}
