package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Caleb on 4/13/2015.
 */
public class Player{
    final int PLAYER_SPEED = 7;
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

    public void moveUp() {
        this.playerY -= PLAYER_SPEED;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_back_spear);
    }

    public void moveLeft() {
        this.playerX -= PLAYER_SPEED;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_left_spear);
    }

    public void moveDown() {
        this.playerY += PLAYER_SPEED;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_front_spear);
    }

    public void moveRight() {
        this.playerX += PLAYER_SPEED;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_right_spear);
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
