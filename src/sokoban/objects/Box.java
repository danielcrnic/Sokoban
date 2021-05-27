package sokoban.objects;

public class Box extends CusObj {

    private final String objectName;

    public Box(int x, int y, String objectName, int defaultTexture, int triggerTexture) {
        super(x, y, defaultTexture, triggerTexture);
        this.objectName = objectName;
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
        return objectName;
    }
}
