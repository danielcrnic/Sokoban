package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.MainMenuComponent;

import java.io.File;
import java.util.ArrayList;

public class Sokoban extends GameFramework {

    private int menuPosition;
    private MainMenuComponent mainMenuComponent;

    public Sokoban() {
        menuPosition = 0;
        mainMenuComponent = new MainMenuComponent("SOKOBAN", new String[]{"START GAME", "CUSTOM GAME", "ABOUT", "EXIT"},
                "V0.1", "(C) 2021, Daniel Crnic and Alfred Mattsson",menuPosition, getTextures().get(0), getTextures().get(4));
        setComponent(mainMenuComponent);
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
        return "Sokoban";
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
