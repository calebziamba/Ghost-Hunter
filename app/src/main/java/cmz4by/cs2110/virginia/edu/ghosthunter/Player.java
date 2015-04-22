package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Caleb on 4/13/2015.
 */
public class Player{
    final int PLAYER_SPEED = 5;
    private int playerX;
    private int playerY;
    private GameView gameView;
    private Bitmap playerImage;


    public Player(GameView v, Bitmap b) {
        this.gameView = v;
        this.playerImage = b;
        this.playerX = 100;
        this.playerY = 100;
    }

    public void draw(Canvas c) {
        c.drawBitmap(this.playerImage, this.getPlayerX(), this.getPlayerY(), null);
    }

    public void moveLeft() {
        this.playerX -= this.PLAYER_SPEED;
    }

    public void moveRight() {
        this.playerX += this.PLAYER_SPEED;
    }

    public void setPlayerX(int x) {
        this.playerX = x;
    }

    public int getPlayerX() {
        Log.d("player", "player position X is " + playerX);
        return this.playerX;

    }

    public void setPlayerY(int y) {
        this.playerY = y;
    }

    public int getPlayerY() {
        Log.d("player", "player position Y is " + this.playerY);
        return this.playerY;
    }

}
