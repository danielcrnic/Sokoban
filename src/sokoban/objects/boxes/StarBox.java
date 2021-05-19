package sokoban.objects.boxes;

import sokoban.objects.CusObj;

import java.awt.*;

import static sokoban.drawcomponent.GameComponent.TEXTURE_STAR;
import static sokoban.drawcomponent.GameComponent.TEXTURE_STAR_MARKED;

public class StarBox extends CusObj {

    private static final int defaultTexture = TEXTURE_STAR;
    private static final int triggerTexture = TEXTURE_STAR_MARKED;

    public StarBox(int x, int y, Color color) {
        super(x, y, defaultTexture, triggerTexture, color);
    }

    public StarBox(int x, int y) {
        super(x, y, defaultTexture, triggerTexture);
    }


    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isSteppable() {
        return false;
    }

    @Override
    public int typeOfObject() {
        return TYPE_BOX;
    }

    @Override
    public String objectName() {
        return "STAR";
    }
}
