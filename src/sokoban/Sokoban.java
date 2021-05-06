import framework.GameFramework;

import java.io.File;
import java.util.ArrayList;

public class Sokoban extends GameFramework {

    public Sokoban() {

    }

    @Override
    public int getGUIWidth() {
        return 500;
    }

    @Override
    public int getGUIHeight() {
        return 500;
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

    }

    @Override
    public void goDown() {

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
