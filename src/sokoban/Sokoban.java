package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.MainMenuComponent;
import sokoban.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static sokoban.drawcomponent.GameComponent.*;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";

    private int menuPosition;
    private MainMenuComponent mainMenuComponent;

    private Font pixelFont;
    private BufferedImage[] textures;

    public Sokoban() {
        // Load fonts and texture, sounds, and music to the corresponding stuff
        pixelFont = loadFont(new File(PATH_TO_FONTS + "Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        loadTextures();

        // menuPosition = 0;
        // mainMenuComponent = new MainMenuComponent(GAME_NAME, new String[]{"START GAME", "CUSTOM GAME", "HOW TO PLAY", "ABOUT", "EXIT"},
        //         VERSION, COPYRIGHT, menuPosition, textures[TEXTURE_FLOOR], textures[TEXTURE_PLAYER], pixelFont);
        // setComponent(mainMenuComponent);

        // CusObj wall = new Floor(getTextures().get(6));

        // CusObj player = new CusObj(getTextures().get(4), false);
        // CusObj floor = new CusObj(getTextures().get(0), false);
        // CusObj box = new CusObj(getTextures().get(2), true);
        // CusObj wall = new CusObj(getTextures().get(6), false);

        // CusObj map1[][] = new CusObj[5][5];

        // map1[0] = new CusObj[]{wall, wall, wall, wall, wall};
        // map1[1] = new CusObj[]{wall, floor, floor, floor, wall};
        // map1[2] = new CusObj[]{wall, floor, box, player, wall};
        // map1[3] = new CusObj[]{wall, floor, floor, floor, wall};
        // map1[4] = new CusObj[]{wall, wall, wall, wall, wall};

        // GameComponent gameComponent = new GameComponent(map1);
        // setComponent(gameComponent);2

    }

    @Override
    public int getGUIWidth() {
        return 700;
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

    }

    @Override
    public void goRight() {

    }

    @Override
    public void goUp() {
        if(menuPosition <= 0)
            menuPosition = 1;
        mainMenuComponent.markSelection(--menuPosition);
    }

    @Override
    public void goDown() {
        if(menuPosition >= 4)
            menuPosition = 3;
        mainMenuComponent.markSelection(++menuPosition);
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
        textures[TEXTURE_STAR_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "star.png"));

        textures[TEXTURE_SQUARE] = loadTexture(new File(PATH_TO_TEXTURES + "square.png"));
        textures[TEXTURE_SQUARE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "squareHole.png"));
        textures[TEXTURE_SQUARE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "squareMarked.png"));

        textures[TEXTURE_CIRCLE] = loadTexture(new File(PATH_TO_TEXTURES + "circle.png"));
        textures[TEXTURE_CIRCLE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "circleHole.png"));
        textures[TEXTURE_CIRCLE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "circleMarked.png"));
    }

}
