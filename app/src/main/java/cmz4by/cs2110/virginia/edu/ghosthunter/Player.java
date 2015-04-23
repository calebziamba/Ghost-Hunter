package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Caleb on 4/13/2015.
 */
public class Player{
    final int PLAYER_SPEED = 10;
    private int playerX;
    private int playerY;
    private GameView gameView;
    private Bitmap playerImage;
    private Rect hitbox;


    public Player(GameView v, Bitmap b) {
        this.gameView = v;
        this.playerImage = b;
        this.playerX = 100;
        this.playerY = 100;
        this.hitbox = new Rect(playerX, playerY, playerX + playerImage.getWidth(), playerY + playerImage.getHeight());
    }

    public void draw(Canvas c) {
        c.drawBitmap(this.playerImage, this.getPlayerX(), this.getPlayerY(), null);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        c.drawRect(hitbox, new Paint());
    }

    public void moveUp() {
        this.playerY -= PLAYER_SPEED;
        if (playerY <= 0)
            this.playerY = 0;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_back_spear);
        updateHitbox();
    }

    public void moveLeft() {
        this.playerX -= PLAYER_SPEED;
        if (playerX <= 0)
            this.playerX = 0;
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_left_spear);
        updateHitbox();
    }

    public void moveDown() {
        this.playerY += PLAYER_SPEED;
        if (playerY >= gameView.getHeight() - playerImage.getHeight())
            this.playerY = gameView.getHeight() - playerImage.getHeight();
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_front_spear);
        updateHitbox();
    }

    public void moveRight() {
        this.playerX += PLAYER_SPEED;
        if (playerX >= gameView.getWidth() - playerImage.getWidth())
            this.playerX = gameView.getWidth() - playerImage.getWidth();
        this.playerImage = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.poliwag_right_spear);
        updateHitbox();
    }

    private void updateHitbox() {
        this.hitbox.set(playerX, playerY, playerX + playerImage.getWidth(), playerY + playerImage.getHeight());
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
