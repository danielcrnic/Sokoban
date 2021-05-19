import sokoban.Level;
import sokoban.objects.CusObj;
import sokoban.objects.FloorObject;
import sokoban.objects.PlayerObject;
import sokoban.objects.StaticObject;
import sokoban.objects.boxes.SquareBox;
import sokoban.objects.boxes.StarBox;
import sokoban.objects.holes.SquareHole;
import sokoban.objects.holes.StarHole;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static sokoban.drawcomponent.GameComponent.*;
import static sokoban.drawcomponent.GameComponent.TEXTURE_WALL;

public class SaveFiles {

    public static void main(String[] args) throws IOException {
        CusObj[][] layout = new CusObj[10][10];
        layout[0] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL),new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};
        layout[1] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[2] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[3] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new StaticObject(0, 0, TEXTURE_WATER),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[4] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[5] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[6] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new StaticObject(0, 0, TEXTURE_WATER), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[7] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[8] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR), new FloorObject(0, 0, TEXTURE_FLOOR),new StaticObject(0, 0, TEXTURE_WALL)};
        layout[9] = new CusObj[]{new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL),new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL), new StaticObject(0, 0, TEXTURE_WALL)};

        CusObj[] boxes = new CusObj[9];

        boxes[0] = new SquareBox(1,4);
        boxes[1] = new SquareBox(2,4);
        boxes[2] = new SquareBox(5,7);
        boxes[3] = new SquareBox(5,8);
        boxes[4] = new StarBox(3,2);
        boxes[5] = new StarBox(5,2);
        boxes[6] = new StarBox(6,3);
        boxes[7] = new StarBox(7,4);
        boxes[8] = new StarBox(7,6);

        CusObj[] holes = new CusObj[9];
        holes[0] = new SquareHole(1,7);
        holes[1] = new SquareHole(2,7);
        holes[2] = new SquareHole(1,8);
        holes[3] = new SquareHole(2,8);
        holes[4] = new StarHole(1,6);
        holes[5] = new StarHole(2,6);
        holes[6] = new StarHole(3,6);
        holes[7] = new StarHole(3,7);
        holes[8] = new StarHole(3,8);

        CusObj player = new PlayerObject(4,4, TEXTURE_PLAYER);
        Level level = new Level(layout, player, holes, boxes);

        FileOutputStream fileOutputStream = new FileOutputStream("simple.lvl");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(level);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

}
