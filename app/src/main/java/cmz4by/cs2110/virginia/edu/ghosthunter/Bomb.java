package cmz4by.cs2110.virginia.edu.ghosthunter;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bomb {
    private float x;
    private float y;
    private Bitmap bmp;


    private int life = 10;
    private List<Bomb> bombs;

    public Bomb(List<Bomb> bombs, GameView gameView, float x, float y, Bitmap bmp) {
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
                gameView.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
                gameView.getHeight() - bmp.getHeight());
        this.bmp = bmp;
        this.bombs = bombs;
    }
    //second constructor
//    public Bomb(GameView gameView, Bitmap bmpBomb) {
//
//        this.bmp = bmpBomb;
//    }

    public void changeLife(int time) {
        this.life = time;
    }

    public void changeImage(Bitmap bmp) {
        this.bmp = bmp;
    }

    public void drawBomb(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void update() {
        if (--life < 1) {
            bombs.remove(this);
        }
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
