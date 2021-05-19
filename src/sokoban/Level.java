package sokoban;

import sokoban.objects.CusObj;

import java.util.ArrayList;

public class Level {

    private final CusObj[][] layout;

    private CusObj player;
    private CusObj[] objects;

    public Level(CusObj[][] layout, CusObj player, CusObj[] objects) {
        this.layout = layout;
        this.player = player;
        this.objects = objects;
    }

    public CusObj[][] getLayout() {
        return layout;
    }

    public CusObj[] getObjects() {
        return objects;
    }

    public CusObj getPlayer() {
        return player;
    }

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

        if (second && alreadySomething(obj.getX() + x, obj.getY() + y)) {
            return false;
        }

        boolean foundMatching = false;
        for (CusObj o : objects) {
            if (o.samePosition(obj.getX() + x, obj.getY() + y)) {
                if (second) {
                    if (o.isSteppable()) {
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
        return true;
    }

    private boolean alreadySomething(int x, int y) {
        for (CusObj o : objects) {
            if (o.samePosition(x, y)) {
                if (o.isMovable()) {
                    return true;
                }
            }
        }
        return false;
    }
}
