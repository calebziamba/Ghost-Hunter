package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Caleb on 4/23/2015.
 */
public class Wall {
    private Rect rect;
    private static int scaleWidth;
    private static int scaleHeight;

    public Wall (int x1, int y1, int x2, int y2) {
        rect = new Rect(x1, y1, x2,y2);
    }

    public static ArrayList<Wall> createWalls(GameView gameView) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        scaleWidth = 1080 / gameView.getWidth();
        scaleHeight = 1920 / gameView.getHeight();

        walls.add(new Wall(800 * scaleWidth, 1200 * scaleHeight, 820*scaleWidth, 1400 * scaleHeight));
        walls.add(new Wall(0 * scaleWidth, 1500 *scaleHeight, 820* scaleWidth, 1520*scaleHeight));
        walls.add(new Wall(200* scaleWidth, 1000 *scaleHeight, 620* scaleWidth, 1020*scaleHeight));
        walls.add(new Wall(350* scaleWidth, 1210 *scaleHeight, 370* scaleWidth, 1360*scaleHeight));
        walls.add(new Wall(275* scaleWidth, 400 *scaleHeight, 295* scaleWidth, 700*scaleHeight));
        walls.add(new Wall(660* scaleWidth, 740 *scaleHeight, 1080* scaleWidth, 760*scaleHeight));
        walls.add(new Wall(750* scaleWidth, 150 *scaleHeight, 770* scaleWidth, 350*scaleHeight));
        return walls;
    }

    public void draw(Canvas c, Paint paint) {
        c.drawRect(rect, paint);
    }
}
