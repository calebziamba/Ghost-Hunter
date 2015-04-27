package cmz4by.cs2110.virginia.edu.ghosthunter;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ghost {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;
    private Rect hitboxFront;
    private Rect hitboxBack;

    public Ghost(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        x = rnd.nextInt(gameView.getWidth() - width);
        y = rnd.nextInt(gameView.getHeight() - height);
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;

        int direction = getAnimationRow();
        if (direction == 3) { // ghost moving up
            hitboxFront = new Rect(x, y, this.width + x, y + this.height / 2);
            hitboxBack = new Rect(x, y + this.height / 2, this.width + x, this.height + y);
        }
        else if (direction == 0) { // ghost moving down
            hitboxFront = new Rect(x, y + this.height / 2, this.width + x, this.height + y);
            hitboxBack = new Rect(x, y, this.width + x, y + this.height / 2);
        }
        else if (direction == 1) { // ghost moving left
            hitboxFront = new Rect(x, y, x + this.width / 2, y + this.height);
            hitboxBack = new Rect(x + this.width / 2, y, x + this.width, y + this.height);
        }
        else { // ghost moving right
            hitboxFront = new Rect(x + this.width / 2, y, x + this.width, y + this.height);
            hitboxBack = new Rect(x, y, x + this.width/2, y + this.height);
        }

    }

    private void update() {
        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
       updateHitboxes();
    }

    private void updateHitboxes() {
        int direction = getAnimationRow();
        if (direction == 3) { // ghost moving up
            hitboxFront.set(x, y, this.width + x, y + this.height / 2);
            hitboxBack.set(x, y + this.height / 2, this.width + x, this.height + y);
        }
        else if (direction == 0) { // ghost moving down
            hitboxFront.set(x, y + this.height / 2, this.width + x, this.height + y);
            hitboxBack.set(x, y, this.width + x, y + this.height / 2);
        }
        else if (direction == 1) { // ghost moving left
            hitboxFront.set(x, y, x + this.width / 2, y + this.height);
            hitboxBack.set(x + this.width / 2, y, x + this.width, y + this.height);
        }
        else { // ghost moving right
            hitboxFront.set(x + this.width / 2, y, x + this.width, y + this.height);
            hitboxBack.set(x, y, x + this.width / 2, y + this.height);
        }
    }

    public void draw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public Rect getHitboxFront() {
        return hitboxFront;
    }

    public Rect getHitboxBack() {
        return hitboxBack;
    }
}