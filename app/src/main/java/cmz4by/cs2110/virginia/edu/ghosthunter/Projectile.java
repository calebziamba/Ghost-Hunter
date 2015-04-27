package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Caleb on 4/26/2015.
 */
public class Projectile {
    private Bitmap bmp;
    private Player player;
    private Rect hitbox;
    private int x;
    private int y;
    private int direction;
    private final int SPEED = 17;

    public Projectile(Bitmap image, Player p) {
        bmp = image;
        player = p;
        direction = player.getDirection(); //0 down, 1 left, 2 right, 3 up

        if (direction == 0) {
            x = player.getPlayerX();
            y = player.getPlayerY() + player.getHeight();
        } else if (direction == 1) {
            x = player.getPlayerX() - bmp.getWidth();
            y = player.getPlayerY();
        } else if (direction == 2) {
            x = player.getPlayerX() + player.getWidth();
            y = player.getPlayerY();
        } else if (direction == 3) {
            x = player.getPlayerX();
            y = player.getPlayerY() - bmp.getHeight();
        }

        hitbox = new Rect(x, y, x + bmp.getWidth(), y + bmp.getHeight());
    }

    public void draw(Canvas c) {
        update();
        c.drawBitmap(bmp, x, y, null);
    }

    private void update() {
        // update image position
        if (direction == 0) {
            y += SPEED;
        } else if (direction == 1) {
            x -= SPEED;
        } else if (direction == 2) {
            x += SPEED;
        } else if (direction == 3){
            y -= SPEED;
        }

        // update hitbox position
        hitbox.set(x, y, x + bmp.getWidth(), y + bmp.getHeight());
    }

    public Rect getHitbox() {
        return this.hitbox;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Bitmap getBmp() {
        return this.bmp;
    }
}
