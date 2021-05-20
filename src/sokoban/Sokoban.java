package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.MainMenuComponent;
import sokoban.objects.*;
import sokoban.objects.boxes.SquareBox;
import sokoban.objects.boxes.StarBox;
import sokoban.objects.holes.SquareHole;
import sokoban.objects.holes.StarHole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import static sokoban.drawcomponent.GameComponent.*;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";

    public static final int MODE_MAIN_MENU = 0;
    public static final int MODE_GAME = 1;

    public static final String[] PAUSE_SELECTION = new String[]{"CONTINUE", "RESTART", "BACK TO MAIN MENU"};
    public static final String[] WIN_SELECTION = new String[]{"NEXT", "MAIN MENU"};

    private int currentMode;
    private int menuPosition;

    private MainMenuComponent mainMenuComponent;
    private GameComponent gameComponent;
    private Level level;

    private Font pixelFont;
    private BufferedImage[] textures;

    private int gameTime;

    public Sokoban() {
        // Load fonts and texture, sounds, and music to the corresponding stuff
        pixelFont = loadFont(new File(PATH_TO_FONTS + "Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        loadTextures();

        currentMode = MODE_GAME;

        Object obj = loadObject(new File("Hello_World.lvl"));

        if (obj instanceof Level) {
            level = (Level) obj;
        }
        else {
            System.out.println("Error!");
            System.exit(-1);
        }

        try {
            gameComponent = new GameComponent(level, textures, pixelFont, Color.ORANGE, Color.RED, PAUSE_SELECTION, WIN_SELECTION);
            setComponent(gameComponent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameTime = 0;
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTime++;
                gameComponent.setCenterText(Integer.toString(gameTime));
                gameComponent.updateTime(gameTime);
            }
        }).start();

        gameComponent.displayMode(MODE_CENTER_TEXT);

        // menuPosition = 0;
        // mainMenuComponent = new MainMenuComponent(GAME_NAME, new String[]{"START GAME", "CUSTOM GAME", "HOW TO PLAY", "ABOUT", "EXIT"},
        //         VERSION, COPYRIGHT, menuPosition, textures[TEXTURE_FLOOR], textures[TEXTURE_PLAYER], pixelFont);
        // setComponent(mainMenuComponent);

    }

    @Override
    public int getGUIWidth() {
        return 1000;
    }

    @Override
    public int getGUIHeight() {
        return 700;
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public void goLeft() {
        switch (currentMode) {
            case MODE_GAME:
                if (level.goLeft()) {
                }
                else {

                }
                gameComponent.update();
                break;
            default:
                // Play error sound
                break;
        }
    }

    @Override
    public void goRight() {
        // FIXME: Add check if a game is in progress
        if (level.goRight()) {
        }
        gameComponent.update();

    }

    @Override
    public void goUp() {
        // FIXME: Add check if a game is in progress
        if (level.goUp()) {
            // Do something else
        }
        else {
            // Play error sound
        }
        gameComponent.update();
        // mainMenuComponent.markSelection(--menuPosition);
    }

    @Override
    public void goDown() {
        // FIXME: Add check if a game is in progress
        if (level.goDown()) {
        }
        gameComponent.update();
        // mainMenuComponent.markSelection(++menuPosition);
    }

    @Override
    public void pressedEnter() {

    }

    private void loadTextures() {
        textures = new BufferedImage[13];
        textures[TEXTURE_PLAYER] = loadTexture(new File(PATH_TO_TEXTURES + "player.png"));
        textures[TEXTURE_WALL] = loadTexture(new File(PATH_TO_TEXTURES + "wall.png"));
        textures[TEXTURE_FLOOR] = loadTexture(new File(PATH_TO_TEXTURES + "blank.png"));
        textures[TEXTURE_WATER] = loadTexture(new File(PATH_TO_TEXTURES + "waterway.png"));

        textures[TEXTURE_STAR] = loadTexture(new File(PATH_TO_TEXTURES + "star.png"));
        textures[TEXTURE_STAR_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "starHole.png"));
        textures[TEXTURE_STAR_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "starMarked.png"));

        textures[TEXTURE_SQUARE] = loadTexture(new File(PATH_TO_TEXTURES + "square.png"));
        textures[TEXTURE_SQUARE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "squareHole.png"));
        textures[TEXTURE_SQUARE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "squareMarked.png"));

        textures[TEXTURE_CIRCLE] = loadTexture(new File(PATH_TO_TEXTURES + "circle.png"));
        textures[TEXTURE_CIRCLE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "circleHole.png"));
        textures[TEXTURE_CIRCLE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "circleMarked.png"));
    }

}
