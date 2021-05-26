package framework;

import framework.inputs.InputObserver;
import framework.inputs.InputSubject;
import framework.inputs.listeners.KeyboardListener;
import framework.storage.Storage;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;

import static java.awt.BorderLayout.PAGE_START;
import static java.awt.BorderLayout.CENTER;

public abstract class GameFramework implements InputObserver {

    // Objects that will hold information
    private final JFrame frame;
    private final InputSubject keySubject;

    private JComponent mainComponent;
    private JMenuBar menuBar;

    private final Storage<BufferedImage> textures;
    private final Storage<Font> fonts;
    private final Storage<MediaPlayer> mediaPlayers;



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
    public GameFramework(boolean loadKeyboardListener) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setMinimumSize(new Dimension(getGUIWidth(), getGUIHeight()));
        frame.setTitle(getGameName());

        mainComponent = null;
        menuBar = null;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        keySubject = new InputSubject(this);
        if (loadKeyboardListener) {
            new KeyboardListener(keySubject);
        }

        textures = new Storage<>();
        fonts = new Storage<>();
        mediaPlayers = new Storage<>();

        new JFXPanel();     // Need to be initialized to be able to use the audio player

        // Call methods that needs to be loaded, if the methods cannot be called. The framework will exit
        // for this feature to work properly, the methods that has the @RequiredLoad annotation will have to
        // throw an exception, or else it is not possible to know if the execution has failed.
        Class<?> clazz = getClass();

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(RequiredLoad.class)) {
                if (m.getExceptionTypes().length <= 0) {
                    System.err.println("WARNING: The method: " + m.getName() + " does not throw a exception if it " +
                            "fails!\nThis is required if the annotation @RequiredLoad should be used.");
                    System.exit(-1);
                }

                try {
                    m.setAccessible(true);
                    m.invoke(this);
                    System.out.println("Loaded successfully: " + m.getName());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Could not load: " + m.getName());
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }

    /**
     * Gets all the files and folders in the specified path. If the parameter is left blank (""), it will return the
     * path from where this program is located at.
     *
     * @param path The path of where to get the files and folders
     * @return An String array with the values
     */
    public String[] getFilesInDirectory(String path) throws NullPointerException {
        return new File(path + ".").list();
    }


    /**
     * Loads the textures into a singleton based storage which prevents files from being duplicated. If multiple
     * loadings are made with the same file but different indexes, it will still accept but will instead merge the
     * indexes with each others (this reduces unnecessary initializations of a same texture).
     *
     * @param file The file to be loaded
     * @param index The index of the file (has to be unique!)
     * @throws Exception If the index already exists
     * @throws IOException If there is a problem loading the texture (file not found or have not access to file)
     */
    public void loadTexture(File file, String index) throws Exception, IOException {
        // Check if file already exits
        if (textures.hasFile(file)) {
            if (!textures.addIndex(file, index)) {
                throw new Exception("Index already exits!");
            }
        }

        BufferedImage bufferedImage = ImageIO.read(file);
        textures.loadObject(bufferedImage, file, index);
    }

    /**
     * Returns the texture that is is loaded by the index. If the texture does not exist (index does not point to
     * anything) it will return null.
     *
     * @param index The index of the stored texture
     * @return The texture as a BufferedImage
     */
    public BufferedImage returnTexture(String index) {
        return textures.getObject(index);
    }

    /**
     * Loads an external font into a Font variable. This makes it possible for the end developer to make add fonts
     * easily. Furthermore, the end developer can use Font methods like .deriveFont() to change the style (bold, italic
     * e.t.c.) and the font size.
     *
     * This font can be then access throughout the index which is assigned. But the index has to be unique and cannot
     * collide with other fonts (to have same index)
     *
     * @param file The filepath of where the font is located.
     * @param index
     * @throws Exception
     * @throws IOException
     * @throws FontFormatException
     */
    public void loadFont(File file, String index) throws Exception, IOException, FontFormatException {
        // Check if file already exits
        if (fonts.hasFile(file)) {
            if (!fonts.addIndex(file, index)) {
                throw new Exception("Index already exits!");
            }
        }

        Font font = Font.createFont(Font.TRUETYPE_FONT, file);
        fonts.loadObject(font, file, index);
    }

    /**
     * @param index
     * @return
     */
    public Font getFont(String index) {
        return fonts.getObject(index);
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
    public void loadSound(File file, String index) throws Exception, NoSuchFileException {
        // Check if file already exits
        if (mediaPlayers.hasFile(file)) {
            if (!mediaPlayers.addIndex(file, index)) {
                throw new Exception("Index already exits!");
            }
            else {
                return;
            }
        }

        try {
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                }
            });

            mediaPlayers.loadObject(mediaPlayer, file, index);
        }
        catch (MediaException e) {
            throw new NoSuchFileException(file.toURI().toString());
        }
    }


    /**
     * @param index
     */
    public void playSound(String index) {
        MediaPlayer mediaPlayer = mediaPlayers.getObject(index);
        if (mediaPlayer == null) {
            return;
        }

        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.seek(mediaPlayer.getStartTime());
        }

        mediaPlayer.play();
    }

    /**
     * @param index
     */
    public void pauseSound(String index) {
        MediaPlayer mediaPlayer = mediaPlayers.getObject(index);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * @param index
     */
    public void stopSound(String index) {
        MediaPlayer mediaPlayer = mediaPlayers.getObject(index);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }    }

    /**
     * @param index
     * @param volume
     */
    public void setVolumeSound(String index, double volume) {
        double toSet = volume;

        if (volume > 1.0) {
            toSet = 1.0;
        }
        else if (volume < 0.0) {
            toSet = 0;
        }

        MediaPlayer mediaPlayer = mediaPlayers.getObject(index);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(toSet);
        }
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
     * @return A InputSubject that can be sent to input listeners to be able to send commands to the game
     */
    public InputSubject getInputSubject() {
        return keySubject;
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
            case UP -> goUp();
            case DOWN -> goDown();
            case LEFT -> goLeft();
            case RIGHT -> goRight();
            case ENTER -> pressedEnter();
            case BACK -> pressedBack();
        }
    }

}
