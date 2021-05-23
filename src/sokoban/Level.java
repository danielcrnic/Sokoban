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
     * @return
     */
    public CusObj[][] getLayout() {
        return layout;
    }

    /**
     * @return
     */
    public CusObj[] getHoles() {
        return holes;
    }

    /**
     * @return
     */
    public CusObj[] getBoxes() {
        return boxes;
    }

    /**
     * @return
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
     * @return
     */
    public int getCorrectMoves() {
        return correctMoves;
    }

    /**
     * @return
     */
    public int getIncorrectMoves() {
        return incorrectMoves;
    }

    /**
     * @return
     */
    public int getTotalMoves() {
        return (correctMoves + incorrectMoves);
    }

    /**
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @param obj
     * @param x
     * @param y
     * @param second
     * @return
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
                // else {
                //     // // FIXME: Den kommer ju aldrig komma in hit ju...
                //     // if (!o.isSteppable()) {
                //     //     foundMatching = true;
                //     //     if (push(o, x, y,true)) {
                //     //         obj.setX(obj.getX() + x);
                //     //         obj.setY(obj.getY() + y);
                //     //         return true;
                //     //     }
                //     // }
                // }
            }
        }

        // if (foundMatching) {
        //     return false;
        // }

        foundMatching = false;
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
     * @param x
     * @param y
     * @return
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
