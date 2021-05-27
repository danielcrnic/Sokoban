package sokoban.objects;

public class Hole extends CusObj {

    private final String objectName;

    public Hole(int x, int y, String objectName, int defaultTexture) {
        super(x, y, defaultTexture);
        this.objectName = objectName;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return true;
    }

    @Override
    public int typeOfObject() {
        return TYPE_HOLE;
    }

    @Override
    public String objectName() {
        return objectName;
    }

}
