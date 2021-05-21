package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.LevelSelectionComponent;
import sokoban.drawcomponent.MainMenuComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static framework.inputs.InputSubject.*;
import static sokoban.drawcomponent.GameComponent.*;

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

    public static final String[] MAIN_MENU_SELECTION = new String[]{"START QUEST","SELECT LEVEL", "HOW TO PLAY", "ABOUT", "EXIT"};
    public static final String[] PAUSE_SELECTION = new String[]{"CONTINUE", "RESTART", "BACK TO MAIN MENU"};
    public static final String[] WIN_SELECTION = new String[]{"NEXT", "MAIN MENU"};

    private int currentMode;
    private int position;

    private MainMenuComponent mainMenuComponent;
    private LevelSelectionComponent levelSelectionComponent;
    private GameComponent gameComponent;
    private Level level;

    private Font pixelFont;
    private BufferedImage[] textures;
    private String[] levelDirectory;
    private String[] levelSelectionArray;

    private int gameTime;
    private int levelLoaded;

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

        // Load the mainMenuComponent
        position = 0;
        mainMenuComponent = new MainMenuComponent(GAME_NAME, MAIN_MENU_SELECTION, VERSION, COPYRIGHT, position,
                textures[TEXTURE_FLOOR], textures[TEXTURE_PLAYER], pixelFont);
        setComponent(mainMenuComponent);




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
            case MODE_GAME_WIN:
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
            case MODE_GAME_WIN:
                break;
        }

    }

    @Override
    public void goUp() {
        switch (currentMode) {
            case MODE_MAIN_MENU:
                mainMenu(UP);
                break;
            case MODE_LEVEL_SELECTION:
                levelSelection(UP);
                break;
            case MODE_GAME:
                game(UP);
                break;
            case MODE_GAME_PAUSE:
                gamePaused(UP);
                break;
            case MODE_GAME_WIN:
                break;
        }
    }

    @Override
    public void goDown() {
        switch (currentMode) {
            case MODE_MAIN_MENU:
                mainMenu(DOWN);
                break;
            case MODE_LEVEL_SELECTION:
                levelSelection(DOWN);
                break;
            case MODE_GAME:
                game(DOWN);
                break;
            case MODE_GAME_PAUSE:
                gamePaused(DOWN);
                break;
            case MODE_GAME_WIN:
                break;
        }
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
            case MODE_GAME_WIN:
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
            case BACK:  // Pause the game FIXME: Add also to pause the time!
                currentMode = MODE_GAME_PAUSE;
                position = 0;
                gameComponent.setPosition(position);
                gameComponent.setDisplayMode(MODE_PAUSE);
                break;
        }

        gameComponent.update();
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
                        return;
                    case 1:     // Restart
                        if (setupSpecificLevel(levelLoaded)) {
                            try {
                                gameComponent = new GameComponent(level, textures, pixelFont, Color.BLUE, Color.MAGENTA,
                                        PAUSE_SELECTION, WIN_SELECTION);
                                setComponent(gameComponent);
                                currentMode = MODE_GAME;
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
                break;
        }

        gameComponent.setPosition(position);
    }

    /**
     *
     */
    private void setupGameQuest() {

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

        return true;
    }

}
