package sokoban.objects;

import java.awt.*;

public class StaticObject extends CusObj{

    public StaticObject(int x, int y, int texture, Color color) {
        super(x, y, texture, color);
    }

    public StaticObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return false;
    }
}
