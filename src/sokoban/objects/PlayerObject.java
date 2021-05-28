package sokoban.objects;

import java.awt.*;

public class PlayerObject extends CusObj{

    public PlayerObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public PlayerObject(int x, int y, int texture, int triggerTexture) {
        super(x, y, texture, triggerTexture);
    }

    public PlayerObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    /**
     * @return Returns a boolean value of whether the object is able to be moved. Being the Player it obviously
     * can be moved.
     */
    @Override
    public boolean isMovable() {
        return true;
    }

    /**
     * @return Returns a boolean value of whether the object can be stood upon, being the player and it being
     * paradoxical for a player to stand on itself, it simply returns false.
     */
    @Override
    public boolean isSteppable() {
        return false;
    }

    /**
     * @return Returns an integer encoded to mean what kind of object this is, being the player it returns a special
     * encoded value meaning "something else" than the usual.
     */
    @Override
    public int typeOfObject() {
        return TYPE_CUSTOM;
    }

    /**
     * @return Returns the name of the object, being the player it never on the receiving end of being acted upon
     * thus this is never used, and returns null, just for the sake of having to be implemented.
     */
    @Override
    public String objectName() {
        return null;
    }
}
