package sokoban;

import java.awt.image.BufferedImage;

public class CusObj {

    private BufferedImage texture;
    private boolean movable;

    public CusObj(BufferedImage texture, boolean movable) {
        this.texture = texture;
        this.movable = movable;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public boolean isMovable() {
        return movable;
    }

}
