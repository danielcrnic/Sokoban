package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.MainMenuComponent;
import sokoban.objects.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    private int menuPosition;
    private MainMenuComponent mainMenuComponent;

    private Font pixelFont;

    public Sokoban() {
        // Load fonts and texture to the corresponding stuff
        // TODO: Add the loading of texture

        pixelFont = loadFont(new File("resources/fonts/Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        menuPosition = 0;
        mainMenuComponent = new MainMenuComponent(GAME_NAME, new String[]{"START GAME", "CUSTOM GAME", "HOW TO PLAY", "ABOUT", "EXIT"},
                VERSION, COPYRIGHT, menuPosition, loadTexture(new File("resources/textures/blank.png")), loadTexture(new File("resources/textures/player.png")), pixelFont);
        setComponent(mainMenuComponent);

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
        mainMenuComponent.markSelection(--menuPosition);
    }

    @Override
    public void goDown() {
        mainMenuComponent.markSelection(++menuPosition);
    }

    @Override
    public void pressedEnter() {

    }

}
