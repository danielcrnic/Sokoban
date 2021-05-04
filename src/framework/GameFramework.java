package framework;

import framework.inputs.InputObserver;
import framework.inputs.InputSubject;
import framework.inputs.listeners.KeyboardListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GameFramework implements InputObserver {

    // Objects that will hold information
    private ArrayList<BufferedImage> textures;

    // Methods for the GUI
    public abstract int getGUIWidth();
    public abstract int getGUIHeight();
    public abstract String getGameName();

    // These methods will be called when the player wants to go a direction
    public abstract void goLeft();
    public abstract void goRight();
    public abstract void goUp();
    public abstract void goDown();

    // TODO: This will have to be implemented later in the development
    // These methods will be called when the player wants to undo/redo a move
    // public abstract void undoMove();
    // public abstract void redoMove();

    public abstract ArrayList<File> getFilePaths();

    /**
     * Constructor for the framework
     */
    public GameFramework() {
        try {
            loadTextures();     // Tries to load the textures into the ArrayList
        } catch (IOException e) {
            System.err.println("Had an problem loading some/all of the textures!");
            e.printStackTrace();
            System.exit(0);     // Stop the program, no point continuing without any textures
        }

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setTitle(getGameName());

        // Some random shit...

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        initializeInput();
    }

    /**
     * Returns the textures in BufferedImage objects.
     *
     * @return An ArrayList with BufferedImage objects
     */
    public ArrayList<BufferedImage> getTextures() {
        return textures;
    }

    /**
     * Triggers actions when the user presses a button or does in a way a move which get registered by the subject.
     *
     * @param button The button being called from one (or more) subjects
     */
    @Override
    public void keyPressed(int button) {
        // In feature, if the "main menu" should not use the keys by the "game mode", a feature could be added that
        // only triggers goUp(), goDown()... IF a game is being played.
        switch (button) {
            case InputSubject.UP:
                goUp();
                break;
            case InputSubject.DOWN:
                goDown();
                break;
            case InputSubject.LEFT:
                goLeft();
                break;
            case InputSubject.RIGHT:
                goRight();
                break;
        }
    }

    /**
     * Loads the textures and stores them in a arraylist
     */
    private void loadTextures() throws IOException {
        textures = new ArrayList<>();   // Initializes the ArrayList
        ArrayList<File> files = getFilePaths();

        for (File f : files) {
            textures.add(ImageIO.read(f));
        }
    }

    private void initializeInput() {
        InputSubject keySubject = new InputSubject(this);
        KeyboardListener keyboardListener = new KeyboardListener(keySubject);
    }
}
