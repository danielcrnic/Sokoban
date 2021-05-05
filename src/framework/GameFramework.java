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

import static java.awt.BorderLayout.PAGE_START;
import static java.awt.BorderLayout.CENTER;

public abstract class GameFramework implements InputObserver {

    // Objects that will hold information
    private ArrayList<BufferedImage> textures;
    private JFrame frame;

    private JComponent mainComponent;
    private JMenuBar menuBar;

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
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setTitle(getGameName());

        mainComponent = null;
        menuBar = null;


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
     * Adds a JMenuBar to the top of the window. If a menuBar already exists, it will replace the current with the new
     * one.
     * <p>
     * For more information about jMenuBar and how it works, please refer to this page:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
     *
     * @param menuBar The JMenuBar to be added into the JFrame at the top
     */
    public void setMenuBar(JMenuBar menuBar) {
        if (this.menuBar != null) {
            frame.remove(this.menuBar);      // Remove the old menuBar
        }
        this.menuBar = menuBar;     // Set the new menuBar
        frame.add(menuBar, PAGE_START);

        // Refreshes the window (JFrame) to display the new menuBar
        frame.repaint();
        frame.revalidate();
    }

    /**
     * Adds a JComponent to the middle of the window. If a JComponent already exists in the window (JFrame), it will be
     * replaced with the new one.
     *
     * @param component The JComponent to be added into the JFrame in the middle
     */
    public void setComponent(JComponent component) {
        if (mainComponent != null) {
            frame.remove(mainComponent);    // Remove the old center component
        }
        mainComponent = component;
        frame.add(mainComponent, CENTER);

        // Refreshes the window (JFrame) to display the new component
        frame.repaint();
        frame.revalidate();
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
     *
     * @throws IOException If one of the files could not be loaded (either not being existing or placed in wrong path)
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
