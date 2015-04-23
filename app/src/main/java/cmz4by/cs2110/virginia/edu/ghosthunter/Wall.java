package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Caleb on 4/23/2015.
 */
public class Wall {
    private Rect rect;

    public Wall (int x1, int y1, int x2, int y2) {
        rect = new Rect(x1, y1, x2,y2);
    }

    public ArrayList<Wall> createWalls() {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        // add the walls to the list
        return walls;
    }
}
