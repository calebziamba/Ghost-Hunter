package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Ghost {
    private int x = 0;
    private int y = 0;
    private int xSpeed = 10;
    private int ySpeed = 10;
    private GameView gameView;
    private Bitmap bmp;

    public Ghost (GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
    }

    public void update() {
        if (x >= gameView.getWidth() - bmp.getWidth()) {
            xSpeed = -10;
        }
        if (x + xSpeed < 0) {
            xSpeed = 10;
        }
        x = x + xSpeed;

        if (y >= gameView.getHeight() - bmp.getHeight()) {
            ySpeed = -10;
        }
        if (y + ySpeed < 0) {
            ySpeed = 10;
        }
        y = y + ySpeed;
    }

    public void draw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }
}