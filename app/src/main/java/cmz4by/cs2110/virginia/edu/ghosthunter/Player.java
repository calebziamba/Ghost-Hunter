package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Caleb on 4/13/2015.
 */
public class Player{
    final int PLAYER_SPEED = 10;
    private int playerX;
    private int playerY;
    private int width;
    private int height;
    private GameView gameView;
    private Bitmap playerImage;
    private Rect hitbox;

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int currentFrame;
    private int direction; // 0 down, 1 left, 2 right, 3 up


    public Player(GameView v, Bitmap b) {
        this.gameView = v;
        this.playerImage = b;
        this.width = playerImage.getWidth() / BMP_COLUMNS;
        this.height = playerImage.getHeight() / BMP_ROWS;
        currentFrame = 0;
        direction = 0;

        this.playerX = 910;
        this.playerY = 1000;
        this.hitbox = new Rect(playerX, playerY, playerX + width, playerY + height);
    }

    public void draw(Canvas c) {
        int srcX = currentFrame * width;
        int srcY = direction * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(playerX, playerY, playerX + width, playerY + height);
        c.drawBitmap(playerImage, src, dst, null);
    }

    public void moveUp() {
        this.playerY -= PLAYER_SPEED;
        if (playerY <= 0)
            this.playerY = 0;
        this.direction = 3;
        updateHitbox();
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void moveLeft() {
        this.playerX -= PLAYER_SPEED;
        if (playerX <= 0)
            this.playerX = 0;
        this.direction = 1;
        updateHitbox();
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void moveDown() {
        this.playerY += PLAYER_SPEED;
        if (playerY >= gameView.getHeight() - playerImage.getHeight())
            this.playerY = gameView.getHeight() - playerImage.getHeight();
        this.direction = 0;
        updateHitbox();
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void moveRight() {
        this.playerX += PLAYER_SPEED;
        if (playerX >= gameView.getWidth() - playerImage.getWidth())
            this.playerX = gameView.getWidth() - playerImage.getWidth();
        this.direction = 2;
        updateHitbox();
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    private void updateHitbox() {
        this.hitbox.set(playerX, playerY, playerX + width, playerY + height);
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

    public Rect getHitbox() {
        return this.hitbox;
    }

}
