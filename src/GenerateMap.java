import sokoban.Level;
import sokoban.Sokoban;
import sokoban.objects.CusObj;
import sokoban.objects.FloorObject;
import sokoban.objects.PlayerObject;
import sokoban.objects.StaticObject;
import sokoban.objects.boxes.CircleBox;
import sokoban.objects.boxes.SquareBox;
import sokoban.objects.boxes.StarBox;
import sokoban.objects.holes.CircleHole;
import sokoban.objects.holes.SquareHole;
import sokoban.objects.holes.StarHole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static sokoban.Sokoban.GameDrawer.*;

public class GenerateMap {

    public static final String FILE_TXT = "simple7";

    public static void main(String[] args) {
        File file = new File(FILE_TXT + ".txt");

        ArrayList<char[]> arrayList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                char[] toChar = new char[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    toChar[i] = line.charAt(i);
                }

                arrayList.add(toChar);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int height = arrayList.size();
        int width = -1;

        char[][] map = new char[arrayList.size()][];
        for (int i = 0; i < map.length; i++) {
            map[i] = arrayList.get(i);
            if (width < arrayList.get(i).length) {
                width = arrayList.get(i).length;
            }
        }

        CusObj[][] layout = new CusObj[height][width];
        ArrayList<CusObj> boxes = new ArrayList<>();
        ArrayList<CusObj> holes = new ArrayList<>();
        CusObj player = null;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 'W':
                        layout[i][j] = new StaticObject(0, 0, TEXTURE_WALL);
                        break;
                    case 'A':
                        layout[i][j] = new StaticObject(0, 0, TEXTURE_WATER);
                        break;
                    case 'P':
                        player = new PlayerObject(j, i, TEXTURE_PLAYER);
                        break;
                    case 'S':
                        boxes.add(new StarBox(j, i));
                        break;
                    case 's':
                        holes.add(new StarHole(j, i));
                        break;
                    case 'Q':
                        boxes.add(new SquareBox(j, i));
                        break;
                    case 'q':
                        holes.add(new SquareHole(j, i));
                        break;
                    case 'C':
                        boxes.add(new CircleBox(j, i));
                        break;
                    case 'c':
                        holes.add(new CircleHole(j, i));
                        break;
                }

                if (layout[i][j] == null && map[i][j] != 'E') {
                    layout[i][j] = new FloorObject(0, 0, TEXTURE_FLOOR);
                }

            }
        }

        CusObj[] boxesArray = new CusObj[boxes.size()];

        int i = 0;
        for (CusObj b : boxes) {
            boxesArray[i] = b;
            i++;
        }

        CusObj[] holesArray = new CusObj[holes.size()];

        i = 0;
        for (CusObj h : holes) {
            holesArray[i] = h;
            i++;
        }

        if (player == null) {
            System.out.println("Error");
            System.exit(-1);
        }
        else {
            try {
                Level level = new Level(layout, player, holesArray, boxesArray);

                FileOutputStream fileOutputStream = new FileOutputStream( FILE_TXT + ".lvl");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(level);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Bruh");
                System.exit(-1);
            }
        }
        new Sokoban();

    }

}
