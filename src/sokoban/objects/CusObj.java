package sokoban.objects;

import java.awt.*;

public abstract class CusObj {

    private int x;
    private int y;

    private final Color color;
    private final int texture;

    public CusObj(int x, int y, int texture, Color color) {
        this.x = x;
        this.y = y;
        this.texture = texture;

        if (color == null) {
            this.color = new Color(0, true);    // Create a transparent color
        }
        else {
            this.color = color;
        }
    }

    public CusObj(int x, int y, int texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.color = new Color(0, true);    // Create a transparent color
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color modifyColor() {
        return color;
    }

    public int getTextureNumber() {
        return texture;
    }

    public abstract boolean isMovable();
    public abstract boolean isSteppable();
}
