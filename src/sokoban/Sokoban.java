package sokoban;

import framework.GameFramework;
import framework.GameUI;
import framework.drawcomponent.GameComponent;
import framework.drawcomponent.LevelSelectionComponent;
import framework.drawcomponent.MainMenuComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static framework.GameUI.SHOW_MAIN_MENU;
import static framework.GameUI.SHOW_SELECTION;
import static framework.inputs.InputSubject.*;
import static framework.drawcomponent.GameComponent.*;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";
    public static final String PATH_TO_LEVELS = "levels/";

    public static final int MODE_MAIN_MENU = 0;
    public static final int MODE_LEVEL_SELECTION = 1;
    public static final int MODE_GAME = 2;
    public static final int MODE_GAME_PAUSE = 3;
    public static final int MODE_GAME_WIN = 4;

    public static final String[] MAIN_MENU_SELECTION = new String[]{"START QUEST","SELECT LEVEL", "HOW TO PLAY",
            "ABOUT", "EXIT"};
    public static final String[] PAUSE_SELECTION = new String[]{"CONTINUE", "RESTART", "BACK TO MAIN MENU"};
    public static final String[] WIN_SELECTION = new String[]{"NEXT", "MAIN MENU"};

    public static final String[] QUEST_LEVELS = new String[]{"simple2.lvl", "simple7.lvl", "simple6.lvl", "simple.lvl",
            "simple3.lvl", "simple4.lvl", "simple8.lvl", "simple9.lvl"};

    private int currentMode;
    private int position;

    private GUI gui;

    private MainMenuComponent mainMenuComponent;
    private LevelSelectionComponent levelSelectionComponent;
    private GameComponent gameComponent;
    private Level level;
    private Timer secondsTimer;

    private Font pixelFont;
    private BufferedImage[] textures;
    private String[] levelDirectory;
    private String[] levelSelectionArray;

    private int gameTime;
    private int levelLoaded;

    private boolean runningQuest;
    private int questLevel;

    public Sokoban() {
        // Load fonts and texture, sounds, and music to the corresponding stuff
        pixelFont = loadFont(new File(PATH_TO_FONTS + "Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        loadTextures();


        // Filters out all non .lvl files and replaces '_' with spaces to show text better
        String[] lvlIndex = getFilesInDirectory(PATH_TO_LEVELS);
        ArrayList<String> lvlDirectory = new ArrayList<>();
        ArrayList<String> selDirectory = new ArrayList<>();

        for (String l : lvlIndex) {
            if (l.endsWith(".lvl")) {
                lvlDirectory.add(l);

                String newString = l.replace('_',' ').substring(0, l.length() - 4);
                selDirectory.add(newString);
            }
        }
        levelDirectory = lvlDirectory.toArray(new String[0]);
        levelSelectionArray = selDirectory.toArray(new String[0]);

        currentMode = MODE_MAIN_MENU;
        runningQuest = false;

        gui = new GUI(SHOW_MAIN_MENU);
        setComponent(gui);

        // gui.setWindow(SHOW_SELECTION);

        // Load the mainMenuComponent
        // position = 0;
        // mainMenuComponent = new MainMenuComponent(GAME_NAME, MAIN_MENU_SELECTION, VERSION, COPYRIGHT, position,
        //         textures[TEXTURE_FLOOR], textures[TEXTURE_PLAYER], pixelFont);
        // setComponent(mainMenuComponent);

        // Create a timer that will be called every second (when in a game), it will update the game time and send the
        // time to the gameComponent which will display
        secondsTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTime++;     // Increment the counter by 1 (counter is in seconds)
                gameComponent.updateTime(gameTime);
            }
        });
    }

    @Override
    public int getGUIWidth() {
        return 1000;
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
        switch (currentMode) {
            case MODE_GAME:
                game(LEFT);
                break;
            case MODE_GAME_PAUSE:
                break;
        }
    }

    @Override
    public void goRight() {
        switch (currentMode) {
            case MODE_LEVEL_SELECTION:
                break;
            case MODE_GAME:
                game(RIGHT);
                break;
        }

    }

    @Override
    public void goUp() {
        gui.goUp();
        // switch (currentMode) {
        //     case MODE_MAIN_MENU:
        //         mainMenu(UP);
        //         break;
        //     case MODE_LEVEL_SELECTION:
        //         levelSelection(UP);
        //         break;
        //     case MODE_GAME:
        //         game(UP);
        //         break;
        //     case MODE_GAME_PAUSE:
        //         gamePaused(UP);
        //         break;
        //     case MODE_GAME_WIN:
        //         gameWin(UP);
        //         break;
        // }
    }

    @Override
    public void goDown() {
        gui.goDown();
        // switch (currentMode) {
        //     case MODE_MAIN_MENU:
        //         mainMenu(DOWN);
        //         break;
        //     case MODE_LEVEL_SELECTION:
        //         levelSelection(DOWN);
        //         break;
        //     case MODE_GAME:
        //         game(DOWN);
        //         break;
        //     case MODE_GAME_PAUSE:
        //         gamePaused(DOWN);
        //         break;
        //     case MODE_GAME_WIN:
        //         gameWin(DOWN);
        //         break;
        // }
    }

    @Override
    public void pressedEnter() {
        switch (currentMode) {
            case MODE_MAIN_MENU:
                mainMenu(ENTER);
                break;
            case MODE_LEVEL_SELECTION:
                levelSelection(ENTER);
                break;
            case MODE_GAME_PAUSE:
                gamePaused(ENTER);
                break;
            case MODE_GAME_WIN:
                gameWin(ENTER);
                break;
        }
    }

    @Override
    public void pressedBack() {
        switch (currentMode) {
            case MODE_GAME:
                game(BACK);
                break;
            case MODE_LEVEL_SELECTION:
                levelSelection(BACK);
                break;
            case MODE_GAME_PAUSE:
                gamePaused(BACK);
                break;
        }
    }

    private void loadTextures() {
        textures = new BufferedImage[13];
        textures[TEXTURE_PLAYER] = loadTexture(new File(PATH_TO_TEXTURES + "player.png"));
        textures[TEXTURE_WALL] = loadTexture(new File(PATH_TO_TEXTURES + "wall.png"));
        textures[TEXTURE_FLOOR] = loadTexture(new File(PATH_TO_TEXTURES + "blank.png"));
        textures[TEXTURE_WATER] = loadTexture(new File(PATH_TO_TEXTURES + "waterway.png"));

        textures[TEXTURE_STAR] = loadTexture(new File(PATH_TO_TEXTURES + "star.png"));
        textures[TEXTURE_STAR_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "starHole.png"));
        textures[TEXTURE_STAR_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "starMarked.png"));

        textures[TEXTURE_SQUARE] = loadTexture(new File(PATH_TO_TEXTURES + "square.png"));
        textures[TEXTURE_SQUARE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "squareHole.png"));
        textures[TEXTURE_SQUARE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "squareMarked.png"));

        textures[TEXTURE_CIRCLE] = loadTexture(new File(PATH_TO_TEXTURES + "circle.png"));
        textures[TEXTURE_CIRCLE_HOLE] = loadTexture(new File(PATH_TO_TEXTURES + "circleHole.png"));
        textures[TEXTURE_CIRCLE_MARKED] = loadTexture(new File(PATH_TO_TEXTURES + "circleMarked.png"));
    }

    /**
     * @param keyInput
     */
    private void mainMenu(int keyInput) {
        if (currentMode != MODE_MAIN_MENU) {
            return;
        }

        switch (keyInput) {
            case UP:
                if (position - 1 >= 0 && position -1 < MAIN_MENU_SELECTION.length) {
                    position--;
                }
                break;
            case DOWN:
                if (position + 1 >= 0 && position + 1 < MAIN_MENU_SELECTION.length) {
                    position++;
                }
                break;
            case ENTER:
                switch (position) {
                    case 0:     // Start quest
                        runningQuest = true;

                        break;
                    case 1:     // Select level
                        position = 0;
                        levelSelectionComponent = new LevelSelectionComponent(levelSelectionArray, position,
                                textures[TEXTURE_FLOOR], pixelFont);
                        currentMode = MODE_LEVEL_SELECTION;
                        setComponent(levelSelectionComponent);
                        return;
                    case 2:     // How to play
                        break;
                    case 3:     // About
                        break;
                    case 4:     // Exit
                        System.exit(0);
                        break;
                }
                break;
            default:
                System.err.println("Incorrect selection!");
                return;
        }

        mainMenuComponent.markSelection(position);

    }

    /**
     * @param keyInput
     */
    private void levelSelection(int keyInput) {
        if (currentMode != MODE_LEVEL_SELECTION) {
            return;
        }

        switch (keyInput) {
            case UP:
                if (position - 1 >= 0 && position - 1 < levelSelectionArray.length) {
                    position--;
                }
                break;
            case DOWN:
                if (position + 1 >= 0 && position + 1 < levelSelectionArray.length) {
                    position++;
                }
                break;
            case ENTER:

                if (setupSpecificLevel(position)) {
                    try {
                        gameComponent = new GameComponent(level, textures, pixelFont, Color.BLUE, Color.MAGENTA,
                                PAUSE_SELECTION, WIN_SELECTION);
                        setComponent(gameComponent);
                        currentMode = MODE_GAME;
                        levelLoaded = position;
                        secondsTimer.restart();       // Start the timer  FIXME: Maybe this will be changed
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("An error occurred!");
                    }
                }
                else {
                    System.err.println("An error occurred!");
                }
                break;
            case BACK:
                position = 1;
                currentMode = MODE_MAIN_MENU;
                setComponent(mainMenuComponent);
                return;
            default:
                System.err.println("Incorrect selection!");
                return;
        }

        levelSelectionComponent.setPosition(position);
    }

    /**
     * @param keyInput
     */
    private void game(int keyInput) {
        if (currentMode != MODE_GAME) {
            return;
        }

        switch (keyInput) {
            case UP:
                level.goUp();
                break;
            case DOWN:
                level.goDown();
                break;
            case LEFT:
                level.goLeft();
                break;
            case RIGHT:
                level.goRight();
                break;
            case BACK:  // Pause the game
                currentMode = MODE_GAME_PAUSE;
                position = 0;
                gameComponent.setPosition(position);
                gameComponent.setDisplayMode(MODE_PAUSE);
                secondsTimer.stop();
                break;
        }

        gameComponent.update();

        if (level.getNumberOfFilledHoles() == level.getNumberOfHoles()) {
            position = 0;
            secondsTimer.stop();
            gameComponent.setPosition(position);
            gameComponent.setDisplayMode(MODE_WIN);
            currentMode = MODE_GAME_WIN;
        }
    }

    /**
     * @param keyInput
     */
    private void gamePaused(int keyInput) {
        if (currentMode != MODE_GAME_PAUSE) {
            return;
        }

        switch (keyInput) {
            case UP:
                if (position - 1 >= 0 && position - 1 < PAUSE_SELECTION.length) {
                    position--;
                }
                break;
            case DOWN:
                if (position + 1 >= 0 && position + 1 < PAUSE_SELECTION.length) {
                    position++;
                }
                break;
            case ENTER:
                switch (position) {
                    case 0:     // Continue
                        gameComponent.setDisplayMode(GameComponent.MODE_GAME);
                        currentMode = MODE_GAME;
                        gameComponent.update();
                        secondsTimer.start();
                        return;
                    case 1:     // Restart
                        if (setupSpecificLevel(levelLoaded)) {
                            try {
                                gameComponent = new GameComponent(level, textures, pixelFont, Color.BLUE, Color.MAGENTA,
                                        PAUSE_SELECTION, WIN_SELECTION);
                                setComponent(gameComponent);
                                currentMode = MODE_GAME;
                                secondsTimer.restart();
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println("An error occurred!");
                            }
                        }
                        break;
                    case 2:     // Back to main menu
                        setComponent(mainMenuComponent);
                        position = 0;
                        mainMenuComponent.markSelection(position);
                        currentMode = MODE_MAIN_MENU;
                        // FIXME: Add also other stuff here that has to be resetted!
                        break;
                    default:
                        System.err.println("Incorrect selection!");
                }
                break;
            case BACK:  // Return back to the game
                gameComponent.setDisplayMode(GameComponent.MODE_GAME);
                currentMode = MODE_GAME;
                gameComponent.update();
                secondsTimer.start();
                return;
        }

        gameComponent.setPosition(position);
    }

    private void gameWin(int keyInput) {
        if (currentMode != MODE_GAME_WIN) {
            return;
        }

        switch (keyInput) {
            case UP:
                if (position - 1 >= 0 && position - 1 < WIN_SELECTION.length) {
                    position--;
                }
                break;
            case DOWN:
                if (position + 1 >= 0 && position + 1 < WIN_SELECTION.length) {
                    position++;
                }
                break;
            case ENTER:
                switch (position) {
                    case 0:
                        if (runningQuest) {

                        }
                        else {  // Goto level selection
                            setComponent(levelSelectionComponent);
                            position = 0;
                            levelSelectionComponent.setPosition(position);
                            currentMode = MODE_LEVEL_SELECTION;
                        }
                        break;
                    case 1:
                        // Goto main menu
                        setComponent(mainMenuComponent);
                        position = 0;
                        mainMenuComponent.markSelection(position);
                        currentMode = MODE_MAIN_MENU;
                        return;
                }
                break;
        }

        gameComponent.setPosition(position);
    }

    /**
     *
     */
    private void setupGameQuest(int level) {

    }

    /**
     * @param arrayIndex
     * @return
     */
    private boolean setupSpecificLevel(int arrayIndex) {
        if (arrayIndex > levelDirectory.length || arrayIndex < 0) {
            return false;
        }

        Object object = loadObject(new File(PATH_TO_LEVELS + levelDirectory[arrayIndex]));
        if (object == null) {
            return false;
        }

        if (object instanceof Level) {
            level = (Level) object;
        }
        else {
            return false;
        }
        gameTime = 0;       // Resets the game time
        return true;
    }

    /**
     * @param filename
     * @return
     */
    private boolean setupSpecificLevel(String filename) {
        Object object = loadObject(new File(PATH_TO_LEVELS + filename));
        if (object == null) {
            return false;
        }

        if (object instanceof Level) {
            level = (Level) object;
        }
        else {
            return false;
        }
        gameTime = 0;       // Resets the game time
        return true;
    }


    public class GUI extends GameUI {

        public GUI(int selectWindow) {
            super(selectWindow);
        }

        @Override
        public String getTitle() {
            return GAME_NAME;
        }

        @Override
        public String getVersion() {
            return VERSION;
        }

        @Override
        public String getCopyrightNotice() {
            return COPYRIGHT;
        }

        @Override
        public Font getFont() {
            return pixelFont;
        }

        @Override
        public String[] getMainMenuOptions() {
            return MAIN_MENU_SELECTION;
        }

        @Override
        public BufferedImage getMainMenuBackground() {
            return textures[TEXTURE_FLOOR];
        }

        @Override
        public BufferedImage getMainMenuPositionImage() {
            return textures[TEXTURE_PLAYER];
        }

        @Override
        public String getSelectionTitle() {
            return "SELECT LEVEL";
        }

        @Override
        public String[] getSelectionOptions() {
            return levelSelectionArray;
        }

        @Override
        public String getSelectionBottomBarText() {
            return "ESC: TO GO BACK   ENTER: SELECT";
        }

        @Override
        public BufferedImage getSelectionBackground() {
            return textures[TEXTURE_FLOOR];
        }

        @Override
        public Color getGameBackgroundColor1() {
            return null;
        }

        @Override
        public Color getGameBackgroundColor2() {
            return null;
        }

        @Override
        public int getGameBlockWidth() {
            return 0;
        }

        @Override
        public int getGameBlockHeight() {
            return 0;
        }

        @Override
        public int[][] getGameLayout() {
            return new int[0][];
        }

        @Override
        public int[][] getGameObjects() {
            return new int[0][];
        }

        @Override
        public int[][] getPlayerObject() {
            return new int[0][];
        }

        @Override
        public BufferedImage getTexture(int i) {
            return null;
        }

    }

}
