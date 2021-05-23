package sokoban;

import framework.drawcomponents.GameComponent;
import framework.GameFramework;
import framework.drawcomponents.ListComponent;
import framework.drawcomponents.MenuComponent;
import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;

import static sokoban.Sokoban.GameDrawer.*;

public class Sokoban extends GameFramework {

    // --- Game information ---
    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    // --- Paths to files ---
    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";
    public static final String PATH_TO_LEVELS = "levels/";

    // -- Selections for the menus ---
    public static final String[] MAIN_MENU_SELECTION = new String[]{"START QUEST", "SELECT LEVEL", "EXIT"};
    public static final String[] PAUSE_SELECTION = new String[]{"CONTINUE", "RESTART LEVEL", "BACK TO MAIN MENU"};

    public static final String[] WIN_SELECTION = new String[]{"BACK TO LEVEL SELECTOR", "BACK TO MAIN MENU"};
    public static final String[] WIN_QUEST_SELECTION = new String[]{"NEXT LEVEL", "BACK TO MAIN MENU"};
    public static final String[] WIN_QUEST_END_SELECTION = new String[]{"VIEW RESULT"};

    public static final String LEVEL_SELECTION_TITLE = "SELECT LEVEL";
    public static final String LEVEL_SELECTION_BOTTOM_BAR_TEXT = "ESC: TO GO BACK   ENTER: SELECT";

    // --- The quest levels (where the first one is the first level) ---
    // public static final String[] QUEST_LEVELS = new String[]{"level1.lvl", "level2.lvl", "level3.lvl"};
    public static final String[] QUEST_LEVELS = new String[]{"level1.lvl", "level2.lvl", "level3.lvl", "level4.lvl", "level5.lvl",
            "level6.lvl", "level7.lvl", "level8.lvl"};

    // --- Status for different menus when controlling the application ---
    public static final int STATUS_MAIN_MENU = 0;
    public static final int STATUS_LEVEL_SELECTOR = 1;
    public static final int STATUS_GAME = 2;
    public static final int STATUS_GAME_PAUSED = 3;
    public static final int STATUS_GAME_WIN = 4;

    // --- Variables for the sounds/music ---
    public static int SOUND_DING;
    public static int SOUND_ERROR;

    private final GameDrawer gameDrawer;
    private final MenuComponent mainMenuDrawer;
    private final ListComponent levelListDrawer;

    private Level level;
    private final Timer secondsTimer;

    private Font pixelFont;
    private BufferedImage[] textures;
    private final String[] levelDirectory;
    private final String[] levelSelectionArray;

    private Color backgroundColor1;
    private Color backgroundColor2;

    private boolean runningQuest, showEndingQuest = false;

    private int gameTime, questLevel, numberOfTries;
    private int gameStatus;

    private int questTotalTime, questTotalCorrectMoves, questTotalIncorrectMoves, questTotalTries;

    private String levelLoaded;

    public Sokoban() {
        // Load fonts and texture, sounds, and music to the corresponding stuff
        pixelFont = loadFont(new File(PATH_TO_FONTS + "Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        loadTextures();     // Load the textures

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

        // Create a timer that can increment a counter every second
        secondsTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTime++;
                gameDrawer.repaint();
            }
        });

        // Load the sounds
        try {
            SOUND_DING = loadSound(new File(PATH_TO_SOUND_EFFECTS + "select.mp3"));
            SOUND_ERROR = loadSound(new File(PATH_TO_SOUND_EFFECTS + "error.mp3"));
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }

        mainMenuDrawer = new MainMenuDrawer();
        levelListDrawer = new LevelSelectorDrawer();
        gameDrawer = new GameDrawer();

        gameStatus = STATUS_MAIN_MENU;
        setComponent(mainMenuDrawer);
    }

    @Override
    public int getGUIWidth() {
        return 1200;
    }

    @Override
    public int getGUIHeight() {
        return 800;
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public void goLeft() {
        if (gameStatus == STATUS_GAME) {
            if (level.goLeft()) {
                gameDrawer.repaint();
                hasCompletedLevel();
            }
            else {
                playSound(SOUND_ERROR);     // Play error sound
            }
        }
    }

    @Override
    public void goRight() {
        if (gameStatus == STATUS_GAME) {
            if (level.goRight()) {
                gameDrawer.repaint();
                hasCompletedLevel();
            }
            else {
                playSound(SOUND_ERROR);     // Play error sound
            }
        }
    }

    @Override
    public void goUp() {
        switch (gameStatus) {
            case STATUS_MAIN_MENU:
                mainMenuDrawer.selectionMoveUp();
                playSound(SOUND_DING);
                break;
            case STATUS_LEVEL_SELECTOR:
                levelListDrawer.selectionMoveUp();
                playSound(SOUND_DING);
                break;
            case STATUS_GAME:
                if (level.goUp()) {
                    gameDrawer.repaint();
                    hasCompletedLevel();
                }
                else {
                    playSound(SOUND_ERROR);     // Play error sound
                }
                break;
            case STATUS_GAME_PAUSED:
            case STATUS_GAME_WIN:
                gameDrawer.selectionMoveUp();
                break;
        }
    }

    @Override
    public void goDown() {
        switch (gameStatus) {
            case STATUS_MAIN_MENU:
                mainMenuDrawer.selectionMoveDown();
                playSound(SOUND_DING);
                break;
            case STATUS_LEVEL_SELECTOR:
                levelListDrawer.selectionMoveDown();
                playSound(SOUND_DING);
                break;
            case STATUS_GAME:
                if (level.goDown()) {
                    gameDrawer.repaint();
                    hasCompletedLevel();
                }
                else {
                    playSound(SOUND_ERROR);     // Play error sound
                }
                break;
            case STATUS_GAME_PAUSED:
            case STATUS_GAME_WIN:
                gameDrawer.selectionMoveDown();
                break;
        }
    }

    @Override
    public void pressedEnter() {
        switch (gameStatus) {
            case STATUS_MAIN_MENU -> mainMenu(mainMenuDrawer.getSelection());
            case STATUS_LEVEL_SELECTOR -> levelSelector(levelListDrawer.getSelection());
            case STATUS_GAME_PAUSED -> gamePaused(gameDrawer.getSelection());
            case STATUS_GAME_WIN -> gameWin(gameDrawer.getSelection());
            default -> playSound(SOUND_ERROR);     // Play error sound

        }
    }

    @Override
    public void pressedBack() {
        switch (gameStatus) {
            case STATUS_LEVEL_SELECTOR -> {
                gameStatus = STATUS_MAIN_MENU;
                setComponent(mainMenuDrawer);
            }
            case STATUS_GAME -> pauseGame();
            case STATUS_GAME_PAUSED -> resumeGame();
            default -> playSound(SOUND_ERROR);     // Play error sound
        }
    }

    // --- Private methods ---

    /**
     * @param selection
     */
    private void mainMenu(int selection) {
        switch (selection) {
            case 0:
                // Start the quest
                if (!startQuest()) {
                    // Throw an error message
                }
                break;
            case 1:
                gameStatus = STATUS_LEVEL_SELECTOR;
                setComponent(levelListDrawer);
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    /**
     * @param selection
     */
    private void levelSelector(int selection) {
        gameTime = 0;   // Reset the game time so new colors get generated
        if (!startGame(levelDirectory[selection])) {
            // Throw an error message
        }
        else {
            // FIXME: Add an error message
        }
    }

    /**
     * @param selection
     */
    private void gamePaused(int selection) {
        switch (selection) {
            case 0 -> resumeGame();
            case 1 -> {
                startGame(levelLoaded);
                gameDrawer.showGame();
            }
            case 2 -> {
                gameStatus = STATUS_MAIN_MENU;
                if (runningQuest) {
                    runningQuest = false;
                }
                setComponent(mainMenuDrawer);
            }
        }
    }

    /**
     * @param selection
     */
    private void gameWin(int selection) {
        if (runningQuest) {
            if (showEndingQuest) {
                runningQuest = false;
                showEndingQuest = false;
                levelLoaded = "";
                gameStatus = STATUS_MAIN_MENU;
                setComponent(mainMenuDrawer);
                return;
            }

            switch (selection) {
                case 0 -> {
                    // Update the values FIXME: Add for tries also later
                    questUpdateTotalValues(gameTime, level.getCorrectMoves(), level.getIncorrectMoves(), numberOfTries);
                    questLevel++;
                    if (questLevel >= QUEST_LEVELS.length) {
                        showEndingQuest = true;     // Finished, go to result page
                        gameDrawer.repaint();
                    } else {
                        gameTime = 0;
                        startGame(QUEST_LEVELS[questLevel]);
                    }
                }
                case 1 -> {
                    runningQuest = false;
                    levelLoaded = "";
                    gameStatus = STATUS_MAIN_MENU;
                    setComponent(mainMenuDrawer);
                }
            }
        }
        else {
            switch (selection) {
                case 0 -> {
                    levelLoaded = "";
                    gameStatus = STATUS_LEVEL_SELECTOR;
                    setComponent(levelListDrawer);
                }
                case 1 -> {
                    levelLoaded = "";
                    gameStatus = STATUS_MAIN_MENU;
                    setComponent(mainMenuDrawer);
                }
            }
        }
    }

    private boolean startGame(String levelSelection) {
        // Load the level
        Object object = loadObject(new File(PATH_TO_LEVELS + levelSelection));

        if (object == null) {
            return false;
        }

        if (object instanceof Level) {
            level = (Level) object;
        }

        if (gameTime == 0) {
            randomizeColors();
            numberOfTries = 1;
        }
        else {
            numberOfTries++;
        }

        gameStatus = STATUS_GAME;
        gameTime = 0;
        levelLoaded = levelSelection;
        secondsTimer.start();

        setComponent(gameDrawer);
        gameDrawer.showGame();
        return true;
    }

    private boolean startQuest() {
        runningQuest = true;

        // Reset the values
        questLevel = 0;
        questTotalTime = 0;
        questTotalCorrectMoves = 0;
        questTotalIncorrectMoves = 0;
        questTotalTries = 0;
        numberOfTries = 0;          // Reset the number of tries

        return startGame(QUEST_LEVELS[questLevel]);
    }

    private void questUpdateTotalValues(int gameTime, int correctMoves, int incorrectMoves, int tries) {
        questTotalTime += gameTime;
        questTotalCorrectMoves += correctMoves;
        questTotalIncorrectMoves += incorrectMoves;
        questTotalTries += tries;
    }

    /**
     * When calling this method, it means that the player wants to pause the game and to display the pause menu
     */
    private void pauseGame() {
        gameStatus = STATUS_GAME_PAUSED;
        gameDrawer.setSelection(0);
        gameDrawer.showPauseMenu();     // Tell the draw component to lay over the pause menu

        secondsTimer.stop();    // Stops the timer
    }

    /**
     * When calling this method, it means that the player wants to go from the pause state back into the playing state,
     * this method will start again the timer (from where it stopped last time)
     */
    private void resumeGame() {
        gameStatus = STATUS_GAME;
        gameDrawer.showGame();  // Tell the draw component to show the game

        secondsTimer.start();   // Starts the timer again
    }

    private void hasCompletedLevel() {
        // Check if the player has won, do not continue if the player has not won
        if (level.getNumberOfHoles() != level.getNumberOfFilledHoles()) {
            return;
        }
        gameStatus = STATUS_GAME_WIN;

        secondsTimer.stop();            // Stop the game counter
        gameDrawer.setSelection(0);
        gameDrawer.showPauseMenu();     // Show the victory overlay
    }


    private void randomizeColors() {
        backgroundColor1 = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        backgroundColor2 = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
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
     *
     */
    public class MainMenuDrawer extends MenuComponent {

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

    }

    /**
     *
     */
    public class LevelSelectorDrawer extends ListComponent {

        @Override
        public Font getFont() {
            return pixelFont;
        }

        @Override
        public String getTitle() {
            return LEVEL_SELECTION_TITLE;
        }

        @Override
        public String[] getOptions() {
            return levelSelectionArray;
        }

        @Override
        public String getBottomBarText() {
            return LEVEL_SELECTION_BOTTOM_BAR_TEXT;
        }

        @Override
        public BufferedImage getBackgroundImage() {
            return textures[TEXTURE_FLOOR];
        }
    }

    /**
     *
     */
    public class GameDrawer extends GameComponent {
        public final static int TEXTURE_PLAYER = 0;
        public final static int TEXTURE_WALL = 1;
        public final static int TEXTURE_FLOOR = 2;
        public final static int TEXTURE_WATER = 3;

        public final static int TEXTURE_STAR = 4;
        public final static int TEXTURE_STAR_HOLE = 5;
        public final static int TEXTURE_STAR_MARKED = 6;

        public final static int TEXTURE_SQUARE = 7;
        public final static int TEXTURE_SQUARE_HOLE = 8;
        public final static int TEXTURE_SQUARE_MARKED = 9;

        public final static int TEXTURE_CIRCLE = 10;
        public final static int TEXTURE_CIRCLE_HOLE = 11;
        public final static int TEXTURE_CIRCLE_MARKED = 12;

        @Override
        public Font getFont() {
            return pixelFont;
        }

        @Override
        public Color getGameBackgroundColor1() {
            return backgroundColor1;
        }

        @Override
        public Color getGameBackgroundColor2() {
            return backgroundColor2;
        }

        @Override
        public int getGameBlockWidth() {
            return level.getLayout()[0].length;
        }

        @Override
        public int getGameBlockHeight() {
            return level.getLayout().length;
        }

        @Override
        public int[][] getBackgroundLayout() {
            CusObj[][] layout = level.getLayout();
            int[][] toTextureNumbers = new int[layout.length][layout[0].length];

            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[i].length; j++) {
                    if (layout[i][j] != null) {
                        toTextureNumbers[i][j] = layout[i][j].getTextureNumber();
                    }
                    else {
                        toTextureNumbers[i][j] = TEXTURE_NONE;
                    }
                }
            }

            return toTextureNumbers;
        }

        @Override
        public int[][] getLayer1() {
            int[][] toTextureNumbers = new int[getGameBlockHeight()][getGameBlockWidth()];

            for (int[] toTextureNumber : toTextureNumbers) {
                Arrays.fill(toTextureNumber, -1);
            }
            for (CusObj h : level.getHoles()) {
                toTextureNumbers[h.getY()][h.getX()] = h.getTextureNumber();
            }

            return toTextureNumbers;
        }

        @Override
        public int[][] getLayer2() {
            CusObj player = level.getPlayer();

            int[][] toTextureNumbers = new int[getGameBlockHeight()][getGameBlockWidth()];

            for (int[] toTextureNumber : toTextureNumbers) {
                Arrays.fill(toTextureNumber, -1);
            }

            toTextureNumbers[player.getY()][player.getX()] = player.getTextureNumber();

            for (CusObj b : level.getBoxes()) {
                toTextureNumbers[b.getY()][b.getX()] = b.getTextureNumber();
            }

            return toTextureNumbers;
        }

        @Override
        public BufferedImage getTexture(int i) {
            return textures[i];
        }

        @Override
        public String getGameTopBarLeftText() {
            return "HOLES: " + level.getNumberOfFilledHoles() + "/" + level.getNumberOfHoles();
        }

        @Override
        public String getGameTopBarMiddleText() {
            if (runningQuest) {
                return "LEVEL " + (questLevel + 1) + "/" + QUEST_LEVELS.length;
            }
            else {
                return levelLoaded.replace('_',' ').substring(0, levelLoaded.length() - 4);
            }
        }

        @Override
        public String getGameTopBarRightText() {
            return "TIME: " + String.format("%02d", (gameTime / 60)) + ":" +
                    String.format("%02d", (gameTime % 60));
        }

        @Override
        public String[] getGameBottomBarLeftTexts() {
            return new String[]{"MOVES: " + level.getCorrectMoves() + "   INCORRECT MOVES: " +
                    level.getIncorrectMoves() + "   TOTAL MOVES: " + level.getTotalMoves()
                    , "AVERAGE MOVES PER SECOND:" + String.format("%.2f", ((float) level.getTotalMoves() / (float) gameTime)) + " MOVES/SEC"};
        }

        @Override
        public int getSecondsBetweenEach() {
            return 10;
        }

        @Override
        public String gamePausedTitle() {
            if (showEndingQuest) {
                return "THE END";
            }
            else if (gameStatus == STATUS_GAME_WIN) {
                return "VICTORY!";
            }
            else {
                return "PAUSED";
            }
        }

        @Override
        public String[] gamePausedDescription() {
            if (showEndingQuest) {
                //FIXME: Add some more stuff here!
                String[] toShow = new String[8];
                toShow[0] = "TOTAL TIME TO COMPLETE ALL LEVELS: " +  String.format("%02d", (questTotalTime / 60)) +
                        ":" + String.format("%02d", (questTotalTime % 60));
                toShow[1] = "";
                toShow[2] = "TOTAL MOVES: " + (questTotalCorrectMoves + questTotalIncorrectMoves);
                toShow[3] = "TOTAL CORRECT MOVES: " + questTotalCorrectMoves + "   TOTAL INCORRECT MOVES: " +
                        questTotalIncorrectMoves;
                toShow[4] = "TOTAL AVERAGE MOVES PER SECOND: " + String.format("%.2f", ((float) (questTotalCorrectMoves
                        + questTotalIncorrectMoves) / (float) questTotalTime)) + " MOVES/SEC";
                toShow[5] = "";
                toShow[6] = "TOTAL NUMBER OF TIRES: " + questTotalTries;
                toShow[7] = "(AVERAGE OF " + String.format("%.2f",(float) questTotalTries / (float) QUEST_LEVELS.length)
                        + " TRIES PER GAME)";

                return toShow;
            }
            else if (gameStatus == STATUS_GAME_WIN) {
                String[] toShow = new String[6];
                toShow[0] = "TIME: " +  String.format("%02d", (gameTime / 60)) + ":" +
                        String.format("%02d", (gameTime % 60));
                toShow[1] = "TOTAL MOVES: " + level.getTotalMoves();
                toShow[2] = "CORRECT MOVES: " + level.getCorrectMoves() + "    INCORRECT MOVES: " +
                        level.getIncorrectMoves();
                toShow[3] = "AVERAGE MOVES PER SECOND: " +
                        String.format("%.2f", ((float) level.getTotalMoves() / (float) gameTime)) + " MOVES/SEC";
                toShow[4] = "";
                toShow[5] = "NUMBER OF TRIES: " + numberOfTries;

                return toShow;
            }
            else {
                return null;
            }
        }

        @Override
        public String[] gamePausedSelections() {
            if (gameStatus == STATUS_GAME_WIN) {
                if (runningQuest) {
                    if (showEndingQuest) {
                        return new String[]{"BACK TO MAIN MENU"};
                    }
                    else if (questLevel + 1 >= QUEST_LEVELS.length) {
                        return WIN_QUEST_END_SELECTION;
                    }
                    else {
                        return WIN_QUEST_SELECTION;
                    }
                }
                else {
                    return WIN_SELECTION;
                }
            }
            else {
                return PAUSE_SELECTION;
            }
        }
    }
}
