package sokoban.objects;

public class Hole extends CusObj {

    private final String objectName;

    public Hole(int x, int y, String objectName, int defaultTexture) {
        super(x, y, defaultTexture);
        this.objectName = objectName;
    }

    /**
     * @return Returns a boolean value of whether the object is able to be moved.
     * Being a hole, it returns false.
     */
    @Override
    public boolean isMovable() {
        return false;
    }

    /**
     * @return Returns a boolean value of whether the object is able to be stood upon.
     * Being a hole, returns true.
     */
    @Override
    public boolean isSteppable() {
        return true;
    }

    /**
     * @return Returns an integer encoded to have special meaning, for this object it means, hole.
     */
    @Override
    public int typeOfObject() {
        return TYPE_HOLE;
    }

    /**
     * @return Returns the name of the object, for this, returns hole.
     */
    @Override
    public String objectName() {
        return objectName;
    }

}
