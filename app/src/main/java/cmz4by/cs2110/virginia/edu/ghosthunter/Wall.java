package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Caleb on 4/23/2015.
 */
public class Wall {
    private Rect rect;
    private static float scaleWidth;
    private static float scaleHeight;

    public Wall (int x1, int y1, int x2, int y2) {
        rect = new Rect(x1, y1, x2,y2);
    }



    public static ArrayList<Wall> createWalls(GameView gameView) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        scaleWidth = (float)gameView.getWidth() / 1080;
        scaleHeight = (float)gameView.getHeight() / 1920;
        Log.d("Wall", "scaling factor for width is " + scaleWidth);
        Log.d("Wall", "scaling factor for width is " + scaleHeight);
        Log.d("Wall", "this gameViiew has width " + gameView.getWidth());
        Log.d("Wall", "this gameViiew has height " + gameView.getHeight());

        walls.add(new Wall(Math.round(800 * scaleWidth), Math.round(1200 * scaleHeight), Math.round(820*scaleWidth), Math.round(1400 * scaleHeight)));
        walls.add(new Wall(Math.round(0*scaleWidth),Math.round(1500*scaleHeight), Math.round(820*scaleWidth), Math.round(1520*scaleHeight)));
        walls.add(new Wall(Math.round(200* scaleWidth), Math.round(1000 *scaleHeight), Math.round(620* scaleWidth), Math.round(1020*scaleHeight)));
        walls.add(new Wall(Math.round(350* scaleWidth), Math.round(1210 *scaleHeight), Math.round(370* scaleWidth), Math.round(1360*scaleHeight)));
        walls.add(new Wall(Math.round(275* scaleWidth), Math.round(400 *scaleHeight),Math.round( 295* scaleWidth), Math.round(700*scaleHeight)));
        walls.add(new Wall(Math.round(660* scaleWidth),Math.round( 740 *scaleHeight),Math.round( 1080* scaleWidth), Math.round(760*scaleHeight)));
        walls.add(new Wall(Math.round(750* scaleWidth), Math.round(150 *scaleHeight), Math.round(770* scaleWidth), Math.round(350*scaleHeight)));
        return walls;
    }



    public void draw(Canvas c, Paint paint) {
        c.drawRect(rect, paint);
    }

    public Rect getRect() {
        return rect;
    }
}
