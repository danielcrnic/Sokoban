package sokoban.objects;

public class Box extends CusObj {

    private final String objectName;

    public Box(int x, int y, String objectName, int defaultTexture, int triggerTexture) {
        super(x, y, defaultTexture, triggerTexture);
        this.objectName = objectName;
    }

    /**
     * @return Returns a boolean value signaling whether a box-object is able to be moved or not.
     * Returns true, becuase a box is able to be pushed.
     */
    @Override

    public boolean isMovable() {
        return true;
    }

    /**
     * @return Returns a boolean value signaling whether a box-object is able to be stood upon or not.
     * Returns false, beacuse a box is not able to be stood upon.
     */
    @Override
    public boolean isSteppable() {
        return false;
    }

    /**
     * @return Returns an int that is encoded to mean that this is a box
     */
    @Override
    public int typeOfObject() {
        return TYPE_BOX;
    }

    /**
     * @return Returns the name of the box object i.e for box: Star, Square, Circle
     */
    @Override
    public String objectName() {
        return objectName;
    }
}
