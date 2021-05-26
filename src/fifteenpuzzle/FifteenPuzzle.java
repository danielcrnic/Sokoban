package fifteenpuzzle;

import framework.GameFramework;
import framework.RequiredLoad;
import framework.drawcomponents.GameComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FifteenPuzzle extends GameFramework {

    private Game game;
    private BufferedImage[] textures;
    private Font pixelFont;

    private int[][] puzzle;
    private boolean hasWon;
    private int posX, posY;

    private int correctMoves, incorrectMoves;
    private int secondsPassed;
    Timer timer;

    public FifteenPuzzle() {
        super(true);

       //  pixelFont = loadFont(new File( "resources/fonts/Pixeled.ttf"));
       //  if (pixelFont == null) {
       //      // Could not find/load the fond
       //      pixelFont = new Font("Cantarell", Font.PLAIN, 12);
       //  }

        // --- UNMARK ONE OF THESE ---
        initializeGame();
        // cheat();

        // Enable the component
        game = new Game();
        setComponent(game);

        // Enable the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                game.repaint();
            }
        });
        timer.start();
    }

    private void checkIfBoardIsSorted() {
        int valueMatch = 1;
        for (int[] ints : puzzle) {
            for (int j = 0; j < puzzle[0].length; j++) {

                if (ints[j] != valueMatch) {
                    return;
                }
                valueMatch++;
                if (valueMatch == 16) {
                    hasWon = true;
                    timer.stop();
                    game.showPauseMenu();
                    return;
                }
            }
        }
    }

    @Override
    public int getGUIWidth() {
        return 900;
    }

    @Override
    public int getGUIHeight() {
        return 700;
    }

    @Override
    public String getGameName() {
        return "15 Puzzle";
    }

    @Override
    public void goLeft() {
        if (!hasWon) {
            if (posX - 1 >= 0) {
                correctMoves++;
                puzzle[posY][posX] = puzzle[posY][posX - 1];
                puzzle[posY][posX - 1] = 0;
                posX--;
                game.repaint();
                checkIfBoardIsSorted();
            } else {
                incorrectMoves++;
            }
        }
        game.repaint();
    }

    @Override
    public void goRight() {
        if (!hasWon) {
            if (posX + 1 <= 3) {
                correctMoves++;
                puzzle[posY][posX] = puzzle[posY][posX + 1];
                puzzle[posY][posX + 1] = 0;
                posX++;
                game.repaint();
                checkIfBoardIsSorted();
            } else {
                incorrectMoves++;
            }
        }
        game.repaint();
    }

    @Override
    public void goUp() {
        if (!hasWon) {
            if (posY - 1 >= 0) {
                correctMoves++;
                puzzle[posY][posX] = puzzle[posY - 1][posX];
                puzzle[posY - 1][posX] = 0;
                posY--;
                game.repaint();
                checkIfBoardIsSorted();
            } else {
                incorrectMoves++;
            }
        }
        game.repaint();
    }

    @Override
    public void goDown() {
        if (!hasWon) {
            if (posY + 1 <= 3) {
                correctMoves++;
                puzzle[posY][posX] = puzzle[posY + 1][posX];
                puzzle[posY + 1][posX] = 0;
                posY++;
                game.repaint();
                checkIfBoardIsSorted();
            } else {
                incorrectMoves++;
            }
        }
        game.repaint();
    }

    @Override
    public void pressedEnter() {
        if (hasWon) {
            System.exit(0);
        }
    }

    @Override
    public void pressedBack() {
        // Not used
    }

    @SuppressWarnings("unused")
    private void initializeGame() {
        puzzle = new int[4][4];
        hasWon = false;
        posX = 3;
        posY = 3;

        correctMoves = 0;
        incorrectMoves = 0;

        // Generate and shuffle numbers
        ArrayList<Integer> shuffle = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            shuffle.add(i);
        }
        Collections.shuffle(shuffle);
        shuffle.add(0);

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                puzzle[i][j] = shuffle.remove(0);
            }
        }
    }

    private void cheat() {
        puzzle = new int[4][4];
        hasWon = false;
        posX = 3;
        posY = 3;

        correctMoves = 0;
        incorrectMoves = 0;

        puzzle[0] = new int[]{1, 2, 3, 4};
        puzzle[1] = new int[]{5, 6, 7, 8};
        puzzle[2] = new int[]{9, 10, 12, 15};
        puzzle[3] = new int[]{13, 14, 11, 0};
    }


    @RequiredLoad
    private void loadTexture() throws IOException {
        textures = new BufferedImage[16];

        // textures[Game.TEXTURE_EMPTY] = loadTexture(new File("resources/fifteenpuzzle/TileBlank.png"));
        // textures[Game.TEXTURE_1] = loadTexture(new File("resources/fifteenpuzzle/TileOne.png"));
        // textures[Game.TEXTURE_2] = loadTexture(new File("resources/fifteenpuzzle/TileTwo.png"));
        // textures[Game.TEXTURE_3] = loadTexture(new File("resources/fifteenpuzzle/TileThree.png"));
        // textures[Game.TEXTURE_4] = loadTexture(new File("resources/fifteenpuzzle/TileFour.png"));
        // textures[Game.TEXTURE_5] = loadTexture(new File("resources/fifteenpuzzle/TileFive.png"));
        // textures[Game.TEXTURE_6] = loadTexture(new File("resources/fifteenpuzzle/TileSix.png"));
        // textures[Game.TEXTURE_7] = loadTexture(new File("resources/fifteenpuzzle/TileSeven.png"));
        // textures[Game.TEXTURE_8] = loadTexture(new File("resources/fifteenpuzzle/TileEight.png"));
        // textures[Game.TEXTURE_9] = loadTexture(new File("resources/fifteenpuzzle/TileNine.png"));
        // textures[Game.TEXTURE_10] = loadTexture(new File("resources/fifteenpuzzle/TileTen.png"));
        // textures[Game.TEXTURE_11] = loadTexture(new File("resources/fifteenpuzzle/TileEleven.png"));
        // textures[Game.TEXTURE_12] = loadTexture(new File("resources/fifteenpuzzle/TileTwelve.png"));
        // textures[Game.TEXTURE_13] = loadTexture(new File("resources/fifteenpuzzle/TileThirteen.png"));
        // textures[Game.TEXTURE_14] = loadTexture(new File("resources/fifteenpuzzle/TileFourteen.png"));
        // textures[Game.TEXTURE_15] = loadTexture(new File("resources/fifteenpuzzle/TileFifteen.png"));
    }

    public class Game extends GameComponent {

        // Textures
        public static final int TEXTURE_EMPTY = 0;
        public static final int TEXTURE_1 = 1;
        public static final int TEXTURE_2 = 2;
        public static final int TEXTURE_3 = 3;
        public static final int TEXTURE_4 = 4;
        public static final int TEXTURE_5 = 5;
        public static final int TEXTURE_6 = 6;
        public static final int TEXTURE_7 = 7;
        public static final int TEXTURE_8 = 8;
        public static final int TEXTURE_9 = 9;
        public static final int TEXTURE_10 = 10;
        public static final int TEXTURE_11 = 11;
        public static final int TEXTURE_12 = 12;
        public static final int TEXTURE_13 = 13;
        public static final int TEXTURE_14 = 14;
        public static final int TEXTURE_15 = 15;

        @Override
        public Font getFont() {
            return pixelFont;
        }

        @Override
        public Color getGameBackgroundColor1() {
            return Color.ORANGE;
        }

        @Override
        public Color getGameBackgroundColor2() {
            return Color.MAGENTA;
        }

        @Override
        public int getGameBlockWidth() {
            return 4;
        }

        @Override
        public int getGameBlockHeight() {
            return 4;
        }

        @Override
        public int[][] getBackgroundLayout() {
            return puzzle;
        }

        @Override
        public int[][] getLayer1() {
            return null;
        }

        @Override
        public int[][] getLayer2() {
            return null;
        }

        @Override
        public BufferedImage getTexture(int i) {
            return textures[i];
        }

        @Override
        public String getGameTopBarLeftText() {
            return null;
        }

        @Override
        public String getGameTopBarMiddleText() {
            return "TIME: " + String.format("%02d", (secondsPassed / 60)) + ":" +
                    String.format("%02d", (secondsPassed % 60));
        }

        @Override
        public String getGameTopBarRightText() {
            return null;
        }

        @Override
        public String[] getGameBottomBarLeftTexts() {
            return new String[]{"MOVES: " + correctMoves + "   INCORRECT MOVES: " + incorrectMoves + "   TOTAL MOVES: "
                    + (correctMoves + incorrectMoves)};
        }

        @Override
        public int getSecondsBetweenEach() {
            return 0;
        }

        @Override
        public String gamePausedTitle() {
            return "CONGRATULATIONS!";
        }

        @Override
        public String[] gamePausedDescription() {
            return new String[]{"TIME IT TOOK: " + String.format("%02d", (secondsPassed / 60)) + ":" +
                    String.format("%02d", (secondsPassed % 60)), "TOTAL MOVES: " + (correctMoves + incorrectMoves),
                    "CORRECT MOVES: " + correctMoves + "   INCORRECT MOVES: " + incorrectMoves,};
        }

        @Override
        public String[] gamePausedSelections() {
            return new String[]{"QUIT GAME"};
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
    }

}
