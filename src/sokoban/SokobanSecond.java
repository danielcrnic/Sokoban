package sokoban;

import framework.GameFramework;
import framework.GameUI;
import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static framework.GameUI.SHOW_MAIN_MENU;
import static sokoban.SokobanSecond.Displayer.*;

public class SokobanSecond extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";
    public static final String PATH_TO_LEVELS = "levels/";

    public static final String[] MAIN_MENU_SELECTION = new String[]{"START QUEST","SELECT LEVEL", "HOW TO PLAY",
            "ABOUT", "EXIT"};
    public static final String[] PAUSE_SELECTION = new String[]{"CONTINUE", "RESTART", "BACK TO MAIN MENU"};
    public static final String[] WIN_SELECTION = new String[]{"NEXT", "MAIN MENU"};

    public static final String[] QUEST_LEVELS = new String[]{"simple2.lvl", "simple7.lvl", "simple6.lvl", "simple.lvl",
            "simple3.lvl", "simple4.lvl", "simple8.lvl", "simple9.lvl"};

    private Displayer displayer;
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


    public SokobanSecond() {
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

        runningQuest = false;

        Object object = loadObject(new File(PATH_TO_LEVELS + levelDirectory[2]));

        if (object instanceof Level) {
            level = (Level) object;
        }

        displayer = new Displayer(SHOW_GAME);
        setComponent(displayer);
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
        level.goLeft();
        displayer.refresh();
    }

    @Override
    public void goRight() {
        level.goRight();
        displayer.refresh();
    }

    @Override
    public void goUp() {
        level.goUp();
        displayer.refresh();
    }

    @Override
    public void goDown() {
        level.goDown();
        displayer.refresh();
    }

    @Override
    public void pressedEnter() {

    }

    @Override
    public void pressedBack() {

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

    public class Displayer extends GameUI {
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


        public Displayer(int selectWindow) {
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
            return level.getLayout()[0].length;
        }

        @Override
        public int getGameBlockHeight() {
            return level.getLayout().length;
        }

        @Override
        public int[][] getGameLayout() {
            CusObj[][] layout = level.getLayout();
            int[][] toTextureNumbers = new int[layout.length][layout[0].length];

            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[i].length; j++) {
                    toTextureNumbers[i][j] = layout[i][j].getTextureNumber();
                }
            }

            return toTextureNumbers;
        }

        @Override
        public int[][] getGameObjects() {

            int[][] toTextureNumbers = new int[getGameBlockHeight()][getGameBlockWidth()];

            for (int[] toTextureNumber : toTextureNumbers) {
                Arrays.fill(toTextureNumber, -1);
            }
            for (CusObj h : level.getHoles()) {
                toTextureNumbers[h.getY()][h.getX()] = h.getTextureNumber();
            }
            for (CusObj b : level.getBoxes()) {
                toTextureNumbers[b.getY()][b.getX()] = b.getTextureNumber();
            }

            return toTextureNumbers;
        }

        @Override
        public int[][] getPlayerObject() {
            CusObj player = level.getPlayer();

            int[][] toTextureNumbers = new int[getGameBlockHeight()][getGameBlockWidth()];

            for (int[] toTextureNumber : toTextureNumbers) {
                Arrays.fill(toTextureNumber, -1);
            }

            toTextureNumbers[player.getY()][player.getX()] = player.getTextureNumber();

            return toTextureNumbers;
        }

        @Override
        public BufferedImage getTexture(int i) {
            return textures[i];
        }
    }
}
