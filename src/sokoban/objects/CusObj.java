package sokoban.objects;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public abstract class CusObj implements Serializable {

    public static final int TYPE_BOX = -1;
    public static final int TYPE_CUSTOM = 0;
    public static final int TYPE_HOLE = 1;

    private int whichTexture = 0;

    private int x;
    private int y;

    private final Color color;
    private final int texture;
    private final int triggerTexture;

    public CusObj(int x, int y, int texture, int triggerTexture, Color color) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.triggerTexture = triggerTexture;

        // Create a transparent color
        this.color = Objects.requireNonNullElseGet(color, () -> new Color(0, true));
    }

    public CusObj(int x, int y, int texture, int triggerTexture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.triggerTexture = triggerTexture;
        this.color = new Color(0, true);    // Create a transparent color
    }

    public CusObj(int x, int y, int texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.triggerTexture = -1;
        this.color = new Color(0, true);    // Create a transparent color
    }

    /**
     * Sets a new x value for the object
     *
     * @param x The new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets a new y value for the object
     *
     * @param y The new y value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Returns the x value of the object
     */
    public int getX() {
        return x;
    }

    /**
     * @return Returns the y value of the object
     */
    public int getY() {
        return y;
    }

    /**
     * @return Returns the Color of the object
     */
    public Color modifyColor() {
        return color;
    }

    /**
     * Returns the selected texture number (index position) of what the texture the object has. This will also check
     * if a trigger texture exists, if not it will return the default texture
     *
     * @return Returns a texture number
     */
    public int getTextureNumber() {
        if (whichTexture == 1 && triggerTexture >= 0) {
            return triggerTexture;
        }
        else {
            return texture;
        }
    }

    /**
     * Changes the texture to use the trigger texture instead (if there is one)
     */
    public void changeToTriggerTexture() {
        whichTexture = 1;
    }

    /**
     * Changes the texture to the default texture
     */
    public void changeToDefaultTexture() {
        whichTexture = 0;
    }

    /**
     * Returns which texture is selected, the integer can be two things:
     *      (0) -> Default texture
     *      (1) -> Trigger texture
     *
     * @return The current selected texture (not the texture ID)
     */
    public int getWhichTexture() {
        return whichTexture;
    }

    /**
     * @param o An object carrying its X and Y positions to be evaluated.
     * @return Returns a boolean value of whether there are objects that would overlap in this new position.
     */
    public boolean samePosition(CusObj o) {
        return o.getY() == getY() && o.getX() == getX();
    }

    /**
     * @param x X position to be evaluated
     * @param y Y position to be evaluated
     * @return Returns a boolean value of whether there would two objects stacked ontop of eachother in this new
     * position. Same as above, but callable with Coordinates rather than an object.
     */
    public boolean samePosition(int x, int y) {
        return x == getX() && y == getY();
    }

    /**
     * @param o Object to be "fitted" into a hole.
     * @return Returns a boolean value of whether the box fit into the hole or not.
     */
    public boolean fitted(CusObj o) {
        // Cannot be the same type of objects
        if (o.typeOfObject() != typeOfObject()) {
            // They cannot be "something else", one has to be a box while the other has to be an hole
            if (o.typeOfObject() != 0 && typeOfObject() != 0) {
                // Checks if their names matches
                if (o.objectName().equals(objectName())) {
                    // Check if their colors match
                    return o.modifyColor().equals(modifyColor());
                }
            }
        }
        return false;
    }

    /**
     * @return Returns a boolean value of whether the object is able to be moved.
     */
    public abstract boolean isMovable();

    /**
     * @return Returns a boolean value of whether the object is able to be stood upon.
     */
    public abstract boolean isSteppable();

    /**
     * An method that signifies what type of object it is, the method should return something of these three things
     *      (-1) -> An box
     *      (0)  -> "Something else"
     *      (1)  -> An hole
     *
     * @return An integer value that signifies what object it is
     */
    public abstract int typeOfObject();

    /**
     * @return Returns the object name
     * */
    public abstract String objectName();
}
