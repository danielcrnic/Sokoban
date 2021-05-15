package sokoban.drawcomponent;

import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {

    private CusObj[][] map;

    private int width;
    private int height;

    public GameComponent(CusObj[][] map) {
        this.map = map;

        width = map[0].length * map[0][0].getTexture().getWidth();
        height = map.length * map[0][0].getTexture().getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int x = 0;
        int y = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                g2.drawImage(map[i][j].getTexture(),null, x, y);
                x += map[i][j].getTexture().getWidth();
            }
            x = 0;
            y += map[i][0].getTexture().getHeight();
        }
    }
}
