package sokoban.objects;

import java.awt.*;

import static sokoban.drawcomponent.GameComponent.TEXTURE_WALL;

public class SteppableObject extends CusObj{

    public SteppableObject(int x, int y, int texture, Color color) {
        super(x, y, texture, color);
    }

    public SteppableObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return true;
    }
}
