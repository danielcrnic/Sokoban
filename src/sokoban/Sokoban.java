package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.MainMenuComponent;
import sokoban.objects.*;

import java.io.File;
import java.util.ArrayList;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    private int menuPosition;
    private MainMenuComponent mainMenuComponent;

    public Sokoban() {
        menuPosition = 0;
        // mainMenuComponent = new MainMenuComponent("SOKOBAN", new String[]{"START GAME", "CUSTOM GAME", "HOW TO PLAY", "ABOUT", "EXIT"},
        //         "V0.1", "(C) 2021, Daniel Crnic and Alfred Mattsson",menuPosition, getTextures().get(0), getTextures().get(4));
        // setComponent(mainMenuComponent);

        CusObj wall = new Floor(getTextures().get(6));

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
        // setComponent(gameComponent);

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

    @Override
    public ArrayList<File> getFilePaths() {
        ArrayList<File> texturesFiles = new ArrayList<>();
        texturesFiles.add(new File("textures/blank.png"));
        texturesFiles.add(new File("textures/blankmarked.png"));
        texturesFiles.add(new File("textures/crate.png"));
        texturesFiles.add(new File("textures/cratemarked.png"));
        texturesFiles.add(new File("textures/player.png"));
        texturesFiles.add(new File("textures/playerflipped.png"));
        texturesFiles.add(new File("textures/wall.png"));
        texturesFiles.add(new File("textures/Waterway.png"));

        return texturesFiles;
    }
}
