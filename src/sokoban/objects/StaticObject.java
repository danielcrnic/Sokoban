package sokoban.objects;

import java.awt.*;

public class StaticObject extends CusObj{

    public StaticObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public StaticObject(int x, int y, int texture, int triggerTexture) {
        super(x, y, texture, triggerTexture);
    }

    public StaticObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    /**
     * @return Returns a boolean value signaling whether the object is able to be moved or not.
     * Being that this is used for the walls, the object will never be moved and thus returns false.
     */
    @Override
    public boolean isMovable() {
        return false;
    }

    /**
     * @return Returns a boolean value of whether the object is able to be stood upon or not, being that this
     * is used for the walls, and they will never be stood upon this returns false.
     */
    @Override
    public boolean isSteppable() {
        return false;
    }

    /**
     * @return Returns an integer encoded to mean what type of object this is, being a wall it will never be acted upon
     * and thus returns
     */
    @Override
    public int typeOfObject() {
        return TYPE_CUSTOM;
    }

    /**
     * @return Returns the name of the object, being a wall that will never be acted upon, this simply returns null.
     */
    @Override
    public String objectName() {
        return null;
    }
}
