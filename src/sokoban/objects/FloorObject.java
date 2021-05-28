package sokoban.objects;

import java.awt.*;

import static sokoban.objects.CusObj.TYPE_CUSTOM;

public class FloorObject extends CusObj {

    public FloorObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public FloorObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    /**
     * @return Returns a boolean value of whether the object is able to be moved.
     * Being a floor the object is not able to be moved, and returns false.
     */
    @Override
    public boolean isMovable() {
        return false;
    }

    /**
     * @return Returns a boolean value of whether the object is able to be stood upon.
     * Being a floor the method returns true.
     */
    @Override
    public boolean isSteppable() {
        return true;
    }

    /**
     * @return Returns an integer that is encoded for a special meaning, to let the program know what kind of
     * object it is.
     */
    @Override
    public int typeOfObject() {
        return TYPE_CUSTOM;
    }

    /**
     * @return Returns a string to signal what kind of object this is, being a floor in the background the value
     * for this is technically "nothing"
     */
    @Override
    public String objectName() {
        return null;
    }

}
