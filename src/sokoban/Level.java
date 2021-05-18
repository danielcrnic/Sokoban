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
        //        int newY = player.getY() - 1;

        return push(player, 0, -1);

        // // Check if touching wall
        // if (!layout[newY][player.getX()].isSteppable()) {
        //     return false;
        // }

        // // FIXME: Add to check if touching an object
        // for (CusObj o : objects) {
        //     if (o.samePosition(player.getX(), newY)) {
        //         if (!o.isSteppable()) {
        //             return false;
        //         }
        //         else if (o.isMovable()) {
        //             // Try to move
        //         }
        //         else {
        //             return false;
        //         }
        //     }
        // }

        // player.setY(newY);
        // return true;
    }

    public boolean goDown() {
        return push(player, 0, 1);

        // int newY = player.getY() + 1;
//
        // // Check if touching wall
        // if (!layout[newY][player.getX()].isSteppable()) {
        //     return false;
        // }
//
        // // FIXME: Add to check if touching an object
//
        // player.setY(newY);
        // return true;
    }

    public boolean goLeft() {
        return push(player, -1, 0);
        // int newX = player.getX() - 1;
//
        // // Check if touching wall
        // if (!layout[player.getY()][newX].isSteppable()) {
        //     return false;
        // }
//
        // // FIXME: Add to check if touching an object
//
        // player.setX(newX);
        // return true;
    }

    public boolean goRight() {
        return push(player,1,0);

        // int newX = player.getX() + 1;
//
        // // Check if touching wall
        // if (!layout[player.getY()][newX].isSteppable()) {
        //     return false;
        // }
//
        // // FIXME: Add to check if touching an object
//
        // player.setX(newX);
        // return true;
    }

    private boolean push(CusObj obj, int x, int y) {
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

        boolean foundMatching = false;
        for (CusObj o : objects) {
            if (o.samePosition(obj.getX() + x, obj.getY() + y)) {
                foundMatching = true;
                if (push(o, x, y)) {
                    obj.setX(obj.getX() + x);
                    obj.setY(obj.getY() + y);
                    return true;
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
}
