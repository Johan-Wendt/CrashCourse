/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crashcourse;

/**
 *
 * @author johanwendt
 */
public interface Constants {
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 700;
    public static final int SOCKET = 8000;
    
    //From player input
    public static final int PLAYER_INPUT_UP_PRESSED = 1;
    public static final int PLAYER_INPUT_RIGHT_PRESSED = 2;
    public static final int PLAYER_INPUT_DOWN_PRESSED = 3;
    public static final int PLAYER_INPUT_LEFT_PRESSED = 4;
    
    public static final int PLAYER_INPUT_UP_RELEASED = 5;
    public static final int PLAYER_INPUT_RIGHT_RELEASED = 6;
    public static final int PLAYER_INPUT_DOWN_RELEASED = 7;
    public static final int PLAYER_INPUT_LEFT_RELEASED = 8;
    
    //To player output
    public static final int ACTION_CREATE_NEW = 0;
    public static final int ACTION_DESTROY_OLD = 1;
    public static final int ACTION_SET_POSITION = 2;
    public static final int ACTION_CHANGE_APPEARANCE = 3;
    public static final int ACTION_PLAY_SOUND = 4;
    
    //VisibleObjectClassNumbers
    public static final int IMAGE_BOMB = 0;
    public static final int IMAGE_CRATER = 10;
    public static final int IMAGE_EXPLOSION = 20;
    public static final int IMAGE_HINDER = 30;
    public static final int IMAGE_HORIZONTAL_FULLSCREN_HINDER = 40;
    public static final int IMAGE_LIFE_METER = 50;
    public static final int IMAGE_MAKE_FASTER_BONUS = 60;
    public static final int IMAGE_SMALL_HINDER = 70;
    public static final int IMAGE_VERTICAL_FULLSCREN_HINDER = 80;
    public static final int IMAGE_PLAYER_ONE_ONE = 100;
    public static final int IMAGE_PLAYER_ONE_TWO = 101;
    public static final int IMAGE_PLAYER_ONE_THREE = 102;
    public static final int IMAGE_PLAYER_ONE_FOUR = 103;
    public static final int IMAGE_PLAYER_ONE_FIVE = 104;
    public static final int IMAGE_PLAYER_ONE_SIX = 105;
    public static final int IMAGE_PLAYER_ONE_SEVEN = 106;
    public static final int IMAGE_PLAYER_ONE_EIGHT = 107;
    public static final int IMAGE_PLAYER_ONE_NINE = 108;
    public static final int IMAGE_PLAYER_ONE_TEN = 109;
    public static final int IMAGE_PLAYER_TWO_ONE = 120;
    public static final int IMAGE_PLAYER_TWO_TWO = 121;
    public static final int IMAGE_PLAYER_TWO_THREE = 122;
    public static final int IMAGE_PLAYER_TWO_FOUR = 123;
    public static final int IMAGE_PLAYER_TWO_FIVE = 124;
    public static final int IMAGE_PLAYER_TWO_SIX = 125;
    public static final int IMAGE_PLAYER_TWO_SEVEN = 126;
    public static final int IMAGE_PLAYER_TWO_EIGHT = 127;
    public static final int IMAGE_PLAYER_TWO_NINE = 128;
    public static final int IMAGE_PLAYER_TWO_TEN = 129;
    
    public static final int SOUND_THUD = 0;
    public static final int SOUND_SQUEEK = 1;
    public static final int SOUND_CRASH = 2;
    public static final int SOUND_FUSE = 3;
    public static final int SOUND_STOP_FUSE = 4;
    public static final int SOUND_EXPLOSION = 5;
    
    public static final int SIZE_PLAYER_ONE_WIDTH = 20;
    public static final int SIZE_PLAYER_ONE_HEIGHT = 40;
    public static final int SIZE_PLAYER_TWO_WIDTH = 20;
    public static final int SIZE_PLAYER_TWO_HEIGHT = 40;
    public static final int SIZE_SMALL_HINDER_WIDTH = 40;
    public static final int SIZE_SMALL_HINDER_HEIGHT = 40;
    public static final int SIZE_HORIZONTAL_FULLSCREEN_HINDER_WIDTH = 1200;
    public static final int SIZE_HORIZONTAL_FULLSCREEN_HINDER_HEIGHT = 40;
    public static final int SIZE_VERTICAL_FULLSCREEN_HINDER_WIDTH = 40;
    public static final int SIZE_VERTICAL_FULLSCREEN_HINDER_HEIGHT = 620;
    public static final int SIZE_MAKE_FASTER_BONUS_WIDTH = 20;
    public static final int SIZE_MAKE_FASTER_BONUS_HEIGHT = 20;
    public static final int SIZE_BOMB_WIDTH = 30;
    public static final int SIZE_BOMB_HEIGHT = 16;
    public static final int SIZE_EXPLOSION_WIDTH = 200;
    public static final int SIZE_EXPLOSION_HEIGHT = 138;
    public static final int SIZE_CRATER_WIDTH = 30;
    public static final int SIZE_CRATER_HEIGHT = 27;
    public static final int SIZE_LIFE_METER_WIDTH = 12;
    public static final int SIZE_LIFE_METER_HEIGHT = 64;
}
