package sokoban;

import sokoban.objects.CusObj;

import java.io.Serializable;

public class Level implements Serializable {

    private final CusObj[][] layout;

    private CusObj player;

    private CusObj[] holes;
    private CusObj[] boxes;

    public Level(CusObj[][] layout, CusObj player, CusObj[] holes, CusObj[] boxes) throws Exception {
        this.layout = layout;
        this.player = player;

        if (holes.length > boxes.length) {
            throw new Exception("There is not enough holes for the boxes!");
        }

        this.holes = holes;
        this.boxes = boxes;
    }

    public CusObj[][] getLayout() {
        return layout;
    }

    public CusObj[] getHoles() {
        return holes;
    }

    public CusObj[] getBoxes() {
        return boxes;
    }

    public CusObj getPlayer() {
        return player;
    }

    public int getNumberOfHoles() {
        return holes.length;
    }

    // public int getNumberOfFilledHoles() {
    //
    // }

    public boolean goUp() {
        return push(player, 0, -1, false);
    }

    public boolean goDown() {
        return push(player, 0, 1, false);
    }

    public boolean goLeft() {
        return push(player, -1, 0, false);
    }

    public boolean goRight() {
        return push(player,1,0, false);
    }

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
                            o.changeToDefaultTexture();
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
                    // FIXME: Detta kommer ju alltid vara false...
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
                            o.changeToDefaultTexture();
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
