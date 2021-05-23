package framework;

import framework.inputs.InputObserver;
import framework.inputs.InputSubject;
import framework.inputs.listeners.KeyboardListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import static java.awt.BorderLayout.PAGE_START;
import static java.awt.BorderLayout.CENTER;

public abstract class GameFramework implements InputObserver {

    // Objects that will hold information
    private final JFrame frame;

    private JComponent mainComponent;
    private JMenuBar menuBar;

    private final ArrayList<MediaPlayer> audioMediaPlayers;

    // Methods for the GUI
    public abstract int getGUIWidth();
    public abstract int getGUIHeight();
    public abstract String getGameName();

    // These methods will be called when the player wants to go a direction
    public abstract void goLeft();
    public abstract void goRight();
    public abstract void goUp();
    public abstract void goDown();
    public abstract void pressedEnter();
    public abstract void pressedBack();

    /**
     * Constructor for the framework
     */
    public GameFramework() {
        // Test the audio capability
        audioMediaPlayers = new ArrayList<>();

        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setMinimumSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setTitle(getGameName());

        mainComponent = null;
        menuBar = null;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        initializeInput();

        new JFXPanel();     // Need to be initialized to be able to use the audio player
    }

    /**
     * Gets all the files and folders in the specified path. If the parameter is left blank (""), it will return the
     * path from where this program is located at.
     *
     * @param path The path of where to get the files and folders
     * @return An String array with the values
     */
    public String[] getFilesInDirectory(String path) {
        return new File(path + ".").list();
    }

    /**
     * Loads an image texture into a BufferedImage variable which is then returned to the developer
     *
     * @return An BufferedImage with the image, if null is returned it means that it could not either find the file
     *         or there was a problem with loading that image.
     */
    public BufferedImage loadTexture(File file) {
        try {
            return ImageIO.read(file);
        }
        catch (IOException e) {
            System.err.println("Had an problem loading the texture!");
            return null;
        }
    }

    /**
     * Loads an external font into a Font variable. This makes it possible for the end developer to make add fonts
     * easily. Furthermore, the end developer can use Font methods like .deriveFont() to change the style (bold, italic
     * e.t.c.) and the font size.
     *
     * @param file The filepath of where the font is located.
     * @return An Font variable with the font, if the font could not be loaded. It will instead return null.
     * */
    public Font loadFont(File file) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (IOException | FontFormatException e) {
            System.err.println("Had an problem loading the font!");
            return null;
        }
    }

    /**
     * Loads an object from a file stored locally. It also checks that the object loaded matches with the same type as
     * the one the user wants.
     *
     * @param file The filepath to be loaded
     * @return Returns an Object of the same type as the type variable. If the type is not the same or the file could
     *         not be loaded, it will return null.
     */
    public Object loadObject(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            return objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Could not find the file: '" + file.getAbsolutePath() + "'");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("The object was created with a older version than this, please recreate the object.");
            return null;
        }
    }

    /**
     * @param file
     * @return
     * @throws NoSuchFileException
     */
    public int loadSound(File file) throws NoSuchFileException {
        try {
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                }
            });

            audioMediaPlayers.add(mediaPlayer);
            return audioMediaPlayers.size() - 1;
        }
        catch (MediaException e) {
            throw new NoSuchFileException(file.toURI().toString());
        }
    }

    /**
     * @param index
     */
    public void playSound(int index) {
        if (audioMediaPlayers.get(index).getStatus() == MediaPlayer.Status.PLAYING) {
            audioMediaPlayers.get(index).seek(audioMediaPlayers.get(index).getStartTime());
        }

        audioMediaPlayers.get(index).play();
    }

    /**
     * @param index
     */
    public void pauseSound(int index) {
        audioMediaPlayers.get(index).pause();
    }

    /**
     * @param index
     */
    public void stopSound(int index) {
        audioMediaPlayers.get(index).stop();
    }

    /**
     * @param index
     * @param volume
     */
    public void setVolumeSound(int index, double volume) {
        double toSet = volume;

        if (volume > 1.0) {
            toSet = 1.0;
        }
        else if (volume < 0.0) {
            toSet = 0;
        }

        audioMediaPlayers.get(index).setVolume(toSet);
    }

    public void toggleAutoplay(int index) {

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
            case InputSubject.UP -> goUp();
            case InputSubject.DOWN -> goDown();
            case InputSubject.LEFT -> goLeft();
            case InputSubject.RIGHT -> goRight();
            case InputSubject.ENTER -> pressedEnter();
            case InputSubject.BACK -> pressedBack();
        }
    }

    private void initializeInput() {
        InputSubject keySubject = new InputSubject(this);
        KeyboardListener keyboardListener = new KeyboardListener(keySubject);
    }
}
