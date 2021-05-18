package sokoban.objects;

import java.awt.*;

public class MovableObject extends CusObj {

    public MovableObject(int x, int y, int texture, Color color) {
        super(x, y, texture, color);
    }

    public MovableObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isSteppable() {
        return false;
    }
}
