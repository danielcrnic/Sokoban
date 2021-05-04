import framework.GameFramework;

import java.io.File;
import java.util.ArrayList;

public class FrameworkTester extends GameFramework {

    private ArrayList<File> texturesFiles;

    @Override
    public int getGUIWidth() {
        return 700;
    }

    @Override
    public int getGUIHeight() {
        return 700 ;
    }

    @Override
    public String getGameName() {
        return "Test game";
    }

    @Override
    public void goLeft() {
        System.out.println("Left");
    }

    @Override
    public void goRight() {
        System.out.println("Right");
    }

    @Override
    public void goUp() {
        System.out.println("Up");
    }

    @Override
    public void goDown() {
        System.out.println("Down");
    }

    @Override
    public ArrayList<File> getFilePaths() {
        texturesFiles = new ArrayList<>();
        texturesFiles.add(new File("textures/blank.png"));
        texturesFiles.add(new File("textures/blankmarked.png"));
        texturesFiles.add(new File("textures/crate.png"));
        texturesFiles.add(new File("textures/cratemarked.png"));
        texturesFiles.add(new File("textures/player.png"));
        texturesFiles.add(new File("textures/playerflipped.png"));
        texturesFiles.add(new File("textures/wall.png"));

        return texturesFiles;
    }

    public static void main(String[] args) {
        FrameworkTester frameworkTester = new FrameworkTester();
    }
}
