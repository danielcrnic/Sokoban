package sokoban;

import sokoban.objects.CusObj;

import java.io.Serializable;

public class Level implements Serializable {

    private final CusObj[][] layout;

    private CusObj player;

    private CusObj[] holes;
    private CusObj[] boxes;

    private int correctMoves, incorrectMoves;

    public Level(CusObj[][] layout, CusObj player, CusObj[] holes, CusObj[] boxes) throws Exception {
        this.layout = layout;
        this.player = player;

        if (holes.length > boxes.length) {
            throw new Exception("There is not enough holes for the boxes!");
        }

        this.holes = holes;
        this.boxes = boxes;
    }

    /**
     * @return The a 2-d CusObj array that essentially is the gameboard.
     */
    public CusObj[][] getLayout() {
        return layout;
    }

    /**
     * @return Returns an array holding the position of the holes in the level.
     */
    public CusObj[] getHoles() {
        return holes;
    }

    /**
     * @return Returns an array holding the position of the boxes in the level.
     */
    public CusObj[] getBoxes() {
        return boxes;
    }

    /**
     * @return Returns a copy of the player in the level.
     */
    public CusObj getPlayer() {
        return player;
    }

    /**
     * @return Number of holes there is on the map
     */
    public int getNumberOfHoles() {
        return holes.length;
    }

    /**
     * @return The number of holes filled with boxes
     */
    public int getNumberOfFilledHoles() {
        int count = 0;
        for (CusObj o : boxes) {
            if (o.getWhichTexture() == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return Returns an integer for how many allowed moves the player made during a level.
     */
    public int getCorrectMoves() {
        return correctMoves;
    }

    /**
     * @return Returns an integer for how many invalid moves the player made during a level.
     */
    public int getIncorrectMoves() {
        return incorrectMoves;
    }

    /**
     * @return Returns the sum of the above two as an integer.
     */
    public int getTotalMoves() {
        return (correctMoves + incorrectMoves);
    }

    /**
     * @return Returns true if it is a valid move to go up. Otherwise it returns false. Also increments correct
     * or incorrect moves counters respectivly.
     */
    public boolean goUp() {
        if (push(player, 0, -1, false)) {
            correctMoves++;
            return true;
        }
        else {
            incorrectMoves++;
            return false;
        }
    }

    /**
     * @return Returns true if it is a valid move to go down. Otherwise it returns false. Also increments correct
     * or incorrect moves counters respectivly.
     */
    public boolean goDown() {
        if (push(player, 0, 1, false)) {
            correctMoves++;
            return true;
        }
        else {
            incorrectMoves++;
            return false;
        }
    }

    /**
     * @return Returns true if it is a valid move to go left. Otherwise it returns false. Also increments correct or
     * incorrect moves counters respectivly.
     */
    public boolean goLeft() {
        if (push(player, -1, 0, false)) {
            correctMoves++;
            return true;
        }
        else {
            incorrectMoves++;
            return false;
        }
    }

    /**
     * @return Returns true if it is a valid move to go right. Otherwise it returns false. Also increments correct or
     * incorrect moves counters respectivly.
     */
    public boolean goRight() {
        if (push(player,1,0, false)) {
            correctMoves++;
            return true;
        }
        else {
            incorrectMoves++;
            return false;
        }
    }

    /**
     * @param obj Object to be pushed, in this case it will most certainly be some kind of box
     * @param x X position for the object to be pushed into
     * @param y Y position for the object to be pushed into
     * @param second Boolean value used to not fall into infinite loop.
     * @return Returns a boolean value of whether the push was successfully or not.
     */
    private boolean push(CusObj obj, int x, int y, boolean second) {
        if ((x != 0 && y != 0)) {
            // Cannot push at a angle!
            return false;
        }

        // Check if the object is pushable
        if (!obj.isMovable()) {
            return obj.isSteppable();
        }

        // Check layout
        if (!layout[obj.getY() + y][obj.getX() + x].isSteppable()) {
            return false;
        }

        // Check if an object already is there
        if (second && alreadySomething(obj.getX() + x, obj.getY() + y)) {
            return false;
        }

        boolean foundMatching = false;
        for (CusObj o : holes) {
            if (o.samePosition(obj.getX() + x, obj.getY() + y)) {
                if (second) {
                    if (o.isSteppable()) {
                        if (o.fitted(obj)) {
                            o.changeToTriggerTexture();
                            obj.changeToTriggerTexture();
                        }
                        else {
                            o.changeToDefaultTexture();
                            obj.changeToDefaultTexture();
                        }

                        obj.setX(obj.getX() + x);
                        obj.setY(obj.getY() + y);
                        return true;
                    }
                    else {
                        return false;
                    }
                }

            }
        }


        for (CusObj o : boxes) {
            if (o.samePosition(obj.getX() + x, obj.getY() + y)) {
                if (second) {
                    if (o.isSteppable()) {
                        if (o.fitted(obj)) {
                            o.changeToTriggerTexture();
                            obj.changeToTriggerTexture();
                        }
                        else {
                            o.changeToDefaultTexture();
                            obj.changeToDefaultTexture();
                        }

                        obj.setX(obj.getX() + x);
                        obj.setY(obj.getY() + y);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    if (!o.isSteppable()) {
                        foundMatching = true;
                        if (push(o, x, y,true)) {
                            obj.setX(obj.getX() + x);
                            obj.setY(obj.getY() + y);
                            return true;
                        }
                    }
                }
            }
        }

        if (foundMatching) {
            return false;
        }

        obj.setX(obj.getX() + x);
        obj.setY(obj.getY() + y);
        obj.changeToDefaultTexture();
        return true;
    }

    /**
     * @param x X position to be evaluated.
     * @param y Y position to be evaluated.
     * @return Returns a boolean value of whether a grid on the gameboard has something in it.
     */
    private boolean alreadySomething(int x, int y) {
        for (CusObj o : boxes) {
            if (o.samePosition(x, y)) {
                if (o.isMovable()) {
                    return true;
                }
            }
        }
        return false;
    }
}
