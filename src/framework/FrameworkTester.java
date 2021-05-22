package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;

public class FrameworkTester extends GameFramework {

    private ArrayList<File> texturesFiles;
    private int count = 1;

    private int song2;
    private int save;

    public FrameworkTester() {
        JPanel label = new JPanel();
        label.setBackground(Color.RED);

        JPanel label2 = new JPanel();
        label2.setBackground(Color.BLUE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Hello 1"));
        menuBar.add(new JMenu("Hello 2"));
        menuBar.add(new JMenu("Hello 3"));
        menuBar.add(new JMenu("Hello 4"));
        menuBar.add(new JMenu("Hello 5"));

        // JMenuBar menuBar2 = new JMenuBar();
        // menuBar2.add(new JMenu("Bruh momento")); // Inte Okej Daniel.. ;)   // Vad annars skulle jag ha skrivit? :P
        //
        // setMenuBar(menuBar);
        // setComponent(label);
        //
        // File file = new File("resources/.");
        //
        // for (String f : file.list()) {
            //     System.out.println(f);
            // }


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
        return "Test game";
    }

    @Override
    public void goLeft() {
        System.out.println("Left");
        playSound(song2);
    }

    @Override
    public void goRight() {
        System.out.println("Right");
        stopSound(song2);
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
    public void pressedEnter() {

    }

    @Override
    public void pressedBack() {

    }

    // @Override
    // public ArrayList<File> getFilePaths() {
    //     texturesFiles = new ArrayList<>();
    //     texturesFiles.add(new File("textures/blank.png"));
    //     texturesFiles.add(new File("textures/blankmarked.png"));
    //     texturesFiles.add(new File("textures/crate.png"));
    //     texturesFiles.add(new File("textures/cratemarked.png"));
    //     texturesFiles.add(new File("textures/player.png"));
    //     texturesFiles.add(new File("textures/playerflipped.png"));
    //     texturesFiles.add(new File("textures/wall.png"));
    //     return texturesFiles;
    // }

    public static void main(String[] args) {
        FrameworkTester frameworkTester = new FrameworkTester();
    }
}
