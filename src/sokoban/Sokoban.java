package sokoban;

import framework.GameFramework;
import sokoban.drawcomponent.GameComponent;
import sokoban.drawcomponent.MainMenuComponent;
import sokoban.objects.*;
import sokoban.objects.boxes.SquareBox;
import sokoban.objects.boxes.StarBox;
import sokoban.objects.holes.SquareHole;
import sokoban.objects.holes.StarHole;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static sokoban.drawcomponent.GameComponent.*;

public class Sokoban extends GameFramework {

    public static final String GAME_NAME = "Sokoban";
    public static final String VERSION = "V0.1";
    public static final String COPYRIGHT = "(C) 2021, Daniel Crnic and Alfred Mattsson";

    public static final String PATH_TO_TEXTURES = "resources/textures/";
    public static final String PATH_TO_FONTS = "resources/fonts/";
    public static final String PATH_TO_SOUND_EFFECTS = "resources/sounds/";
    public static final String PATH_TO_MUSIC = "resources/music/";

    public static final int MODE_MAIN_MENU = 0;
    public static final int MODE_GAME = 1;


    private int currentMode;
    private int menuPosition;

    private MainMenuComponent mainMenuComponent;
    private GameComponent gameComponent;
    private Level level;

    private Font pixelFont;
    private BufferedImage[] textures;

    public Sokoban() {
        // Load fonts and texture, sounds, and music to the corresponding stuff
        pixelFont = loadFont(new File(PATH_TO_FONTS + "Pixeled.ttf"));
        if (pixelFont == null) {
            // Could not find/load the fond
            pixelFont = new Font("Cantarell", Font.PLAIN, 12);
        }

        loadTextures();

        currentMode = MODE_GAME;

        // CusObj[][] layout = new CusObj[7][5];
        // layout[0] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[1] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[2] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[3] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[4] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[5] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new SteppableObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WALL)};
        // layout[6] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};
        // CusObj[] objs = new CusObj[4];
        // objs[1] = new MovableObject(1,2, TEXTURE_CIRCLE);
        // objs[0] = new SteppableObject(1,3, TEXTURE_CIRCLE_HOLE);
        // objs[3] = new MovableObject(3,2, TEXTURE_SQUARE);
        // objs[2] = new SteppableObject(3,3, TEXTURE_SQUARE_HOLE);
        // CusObj player = new MovableObject(2,2, TEXTURE_PLAYER);

        CusObj[][] layout = new CusObj[10][10];
        layout[0] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL),new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};
        layout[1] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[2] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[3] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new StaticObject(0, 0, TEXTURE_WATER),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[4] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[5] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[6] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[7] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[8] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[9] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL),new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};

        CusObj[] boxes = new CusObj[9];

        boxes[0] = new SquareBox(1,4);
        boxes[1] = new SquareBox(2,4);
        boxes[2] = new SquareBox(5,7);
        boxes[3] = new SquareBox(5,8);
        boxes[4] = new StarBox(3,2);
        boxes[5] = new StarBox(5,2);
        boxes[6] = new StarBox(6,3);
        boxes[7] = new StarBox(7,4);
        boxes[8] = new StarBox(7,6);

        CusObj[] holes = new CusObj[9];
        holes[0] = new SquareHole(1,7);
        holes[1] = new SquareHole(2,7);
        holes[2] = new SquareHole(1,8);
        holes[3] = new SquareHole(2,8);
        holes[4] = new StarHole(1,6);
        holes[5] = new StarHole(2,6);
        holes[6] = new StarHole(3,6);
        holes[7] = new StarHole(3,7);
        holes[8] = new StarHole(3,8);

        CusObj player = new PlayerObject(4,4, TEXTURE_PLAYER);

        level = new Level(layout, player, holes, boxes);

        try {
            gameComponent = new GameComponent(level, textures);
            setComponent(gameComponent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // menuPosition = 0;
        // mainMenuComponent = new MainMenuComponent(GAME_NAME, new String[]{"START GAME", "CUSTOM GAME", "HOW TO PLAY", "ABOUT", "EXIT"},
        //         VERSION, COPYRIGHT, menuPosition, textures[TEXTURE_FLOOR], textures[TEXTURE_PLAYER], pixelFont);
        // setComponent(mainMenuComponent);

    }

    @Override
    public int getGUIWidth() {
        return 700;
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
                if (level.goLeft()) {
                    gameComponent.update();
                }
                else {

                }
                break;
            default:
                // Play error sound
                break;
        }
    }

    @Override
    public void goRight() {
        // FIXME: Add check if a game is in progress
        if (level.goRight()) {
            gameComponent.update();
        }
    }

    @Override
    public void goUp() {
        // FIXME: Add check if a game is in progress
        if (level.goUp()) {
            gameComponent.update();
        }
        else {
            // Play error sound
        }
        // mainMenuComponent.markSelection(--menuPosition);
    }

    @Override
    public void goDown() {
        // FIXME: Add check if a game is in progress
        if (level.goDown()) {
            gameComponent.update();
        }

        // mainMenuComponent.markSelection(++menuPosition);
    }

    @Override
    public void pressedEnter() {

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

}
