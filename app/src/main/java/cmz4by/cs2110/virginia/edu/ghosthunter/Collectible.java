package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Caleb on 4/26/2015.
 */
public class Collectible {
    private Bitmap bmp;
    private int x;
    private int y;
    private Rect hitbox;
    public Collectible(Bitmap image, int xPos, int yPos) {
        bmp = image;
        x = xPos;
        y = yPos;
        hitbox = new Rect(x, y, bmp.getWidth() + x, bmp.getHeight() + y);
    }

    public void draw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }

    public Rect getHitbox() {
        return this.hitbox;
    }
}
