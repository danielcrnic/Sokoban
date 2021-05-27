package sokoban.objects;

import static sokoban.Sokoban.GameDrawer.*;

public class ObjectFactory {

    /**
     * Returns an object by the selection, the following selections can be made:
     *      - (W) -> Wall (non steppable)
     *      - (A) -> Water (non steppable)
     *      - (F) -> Floor (steppable)
     *
     *      - (P) -> Player
     *
     *      - (S) -> Star box
     *      - (s) -> Star hole
     *
     *      - (Q) -> Square box
     *      - (q) -> Square hole
     *
     *      - (C) -> Circle box
     *      - (c) -> Circle hole
     * (Note that the selection is case sensitive!)
     *
     * Along with the object type, the x and y position of where the object is located has to also be provided to be
     * able to get an CusObj.
     *
     *
     * @param selection The selection which is specified above
     * @param x The x position (horizontal)
     * @param y The y position (vertical)
     * @return An CusObj with the specified type, if an incorrect type is selected, it will return null.
     */
    public CusObj create(String selection, int x, int y) {
        return switch (selection) {
            case "W" -> new StaticObject(x, y, TEXTURE_WALL);
            case "A" -> new StaticObject(x, y, TEXTURE_WATER);
            case "F" -> new FloorObject(x, y, TEXTURE_FLOOR);
            case "P" -> new PlayerObject(x, y, TEXTURE_PLAYER);
            case "S" -> new Box(x, y, "STAR", TEXTURE_STAR, TEXTURE_STAR_MARKED);
            case "Q" -> new Box(x, y, "SQUARE", TEXTURE_SQUARE, TEXTURE_SQUARE_MARKED);
            case "C" -> new Box(x, y, "CIRCLE", TEXTURE_CIRCLE, TEXTURE_CIRCLE_MARKED);
            case "s" -> new Hole(x, y, "STAR", TEXTURE_STAR_HOLE);
            case "q" -> new Hole(x, y, "SQUARE", TEXTURE_SQUARE_HOLE);
            case "c" -> new Hole(x, y, "CIRCLE", TEXTURE_CIRCLE_HOLE);
            default -> null;
        };
    }

}
