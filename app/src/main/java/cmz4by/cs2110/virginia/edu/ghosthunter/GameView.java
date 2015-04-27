
package cmz4by.cs2110.virginia.edu.ghosthunter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class GameView extends SurfaceView implements SurfaceHolder.Callback  {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Ghost> ghosts = new ArrayList<Ghost>();
    private Player player;

    private int x = 0;
    private Rect wall1;
    private Rect wall2;
    private Rect wall3;
    private Rect wall4;
    private Rect wall5;
    private Rect wall6;
    private Rect wall7;

    //button spaces
    private Rect upSpace;
    private Rect leftSpace;
    private Rect downSpace;
    private Rect rightSpace;
    private Rect quitSpace;
    private Rect attackSpace;

    private Bitmap arrowUp;
    private Bitmap arrowDown;
    private Bitmap arrowRight;
    private Bitmap arrowLeft;
    private Bitmap attackButton;
    private Bitmap quitGame;
    private Bitmap pauseGame;

    long score = 0;
    private Context context;

    private boolean buttonPressed = false;
    boolean spawnGhost = false;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(this);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_sprite_sheet);
        player = new Player(this, bmp);

        arrowDown = BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow);
        arrowUp = BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow);
        arrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
        arrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
        attackButton = BitmapFactory.decodeResource(getResources(), R.drawable.attack_button);



        wall1 = new Rect(800, 1200, 820, 1400);
        wall2 = new Rect(0, 1500, 820, 1520);
        wall3 = new Rect(200, 1000, 620, 1020);
        wall4 = new Rect(350, 1210, 370, 1360);
        wall5 = new Rect(275, 400, 295, 700);
        wall6 = new Rect(660, 740, 1080, 760);
        wall7 = new Rect(750, 150, 770, 350);
        //button spaces (each arrow is 100x100; the quit and pause buttons are 150x150
        quitSpace = new Rect(20, 1730, 170, 1880);

    }
//max X is 1080, max Y is 1535

    public long getScore() {
        return this.score;
    }

    public void increaseScore(long sc){
        score = sc;
    }
    @Override
    public void draw(Canvas c) {

        Paint myPaint = new Paint();
        myPaint.setTextSize(200);
        myPaint.setColor(Color.BLACK);
        c.drawColor(Color.RED);

        myPaint.setTextSize(80);
        c.drawText("Score: " + score, 50, 200, myPaint);


        //walls
        c.drawRect(wall1, myPaint);
        c.drawRect(wall2, myPaint);
        c.drawRect(wall3, myPaint);
        c.drawRect(wall4, myPaint);
        c.drawRect(wall5, myPaint);
        c.drawRect(wall6, myPaint);
        c.drawRect(wall7, myPaint);


        Log.d("size", "screen width is: " + this.getWidth() + "\n and height is: " + this.getHeight());
        // spaces for buttons
        c.drawBitmap(arrowRight, this.getWidth() - arrowRight.getWidth(), this.getHeight() / 2, myPaint);
        c.drawBitmap(arrowDown, this.getWidth() - arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2 + arrowRight.getHeight(), myPaint);
        c.drawBitmap(arrowLeft, this.getWidth() - 2 * arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2, myPaint);
        c.drawBitmap(arrowUp, this.getWidth() - arrowRight.getWidth() - arrowDown.getWidth(), this.getHeight() / 2 - arrowUp.getHeight(), myPaint);
        c.drawBitmap(attackButton, this.getWidth() - arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2, myPaint);

        player.draw(c);
        if (this.score % 5 != 0) {
            spawnGhost = true;
        }
        if (this.score % 5 == 0 && spawnGhost) {
            ghosts.add(new Ghost(this, BitmapFactory.decodeResource(getResources(), R.drawable.ghostarray)));
            spawnGhost = false;
        }

        for (int i = ghosts.size() - 1; i > 0; i--) {
            Ghost ghost = ghosts.get(i);

            // check for if player and ghosts collide
            if (Rect.intersects(player.getHitbox(), ghost.getHitboxBack())) {
                ghosts.remove(i); // kill
                this.score += 5;
            }
            if (Rect.intersects(player.getHitbox(), ghost.getHitboxFront())) {
                gameOver(c, myPaint);
                return;
            }
            ghost.draw(c);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        createSprites();
        gameLoopThread.setRunning(true);
        gameLoopThread.start();


        downSpace = new Rect(this.getWidth() - arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2 + arrowRight.getHeight(),
                                this.getWidth() - arrowRight.getWidth() - attackButton.getWidth() + arrowDown.getWidth(),
                                this.getHeight() / 2 + arrowRight.getHeight() + arrowDown.getHeight());
        leftSpace = new Rect(this.getWidth() - 2 * arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2,
                                this.getWidth() - arrowRight.getWidth() - attackButton.getWidth(), this.getHeight() / 2 + arrowLeft.getHeight());
        rightSpace = new Rect(this.getWidth() - arrowRight.getWidth(), this.getHeight() / 2,
                                this.getWidth(), this.getHeight() / 2 + arrowRight.getHeight());
        upSpace = new Rect(this.getWidth() - arrowRight.getWidth() - arrowDown.getWidth(), this.getHeight() / 2 - arrowUp.getHeight(),
                                this.getWidth() - arrowRight.getWidth(), this.getHeight() / 2);
        attackSpace = new Rect(this.getWidth() - arrowRight.getWidth() - attackButton.getWidth(), this.getHeight()/2,
                                    this.getWidth() - arrowRight.getWidth(), this.getHeight() / 2 + attackButton.getHeight());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    private void createSprites() {
        ghosts.add(createSprite(R.drawable.ghostarray));
        ghosts.add(createSprite(R.drawable.ghostarray));
        ghosts.add(createSprite(R.drawable.ghostarray));
        ghosts.add(createSprite(R.drawable.ghostarray));
        ghosts.add(createSprite(R.drawable.ghostarray));
    }

    private Ghost createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Ghost(this,bmp);
    }

    public void gameOver(Canvas c, Paint myPaint) {
        gameLoopThread.setRunning(false);
        c.drawColor(Color.RED);
        myPaint.setTextSize(150);
        myPaint.setFakeBoldText(true);
        c.drawText("GAME OVER", this.getWidth() / 6, this.getHeight() / 3, myPaint);
        myPaint.setFakeBoldText(false);
        myPaint.setTextSize(80);
        c.drawText("Your score was: " + score, this.getWidth()/4, this.getHeight()/2, myPaint);


    }


    // handles "button" presses
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        if(rightSpace.contains((int) event.getX(), (int) event.getY())) {
            buttonPressed = true;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    new Thread(new Runnable() {
                        public void run() {
                            long ticksPS = 1000 / GameLoopThread.FPS;
                            long startTime;
                            long sleepTime;

                            while(buttonPressed) {
                                startTime = System.currentTimeMillis();

                                player.moveRight();
                                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                                try {
                                    if (sleepTime > 0)
                                        Thread.sleep(sleepTime);
                                    else
                                        Thread.sleep(10);

                                } catch (Exception e) {};
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                    buttonPressed = false;
                    return true;
                }
            }
        else if (leftSpace.contains((int) event.getX(), (int) event.getY())) {
            buttonPressed = true;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    new Thread(new Runnable() {
                        public void run() {
                            long ticksPS = 1000 / GameLoopThread.FPS;
                            long startTime;
                            long sleepTime;

                            while(buttonPressed) {
                                startTime = System.currentTimeMillis();

                                player.moveLeft();
                                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                                try {
                                    if (sleepTime > 0)
                                        Thread.sleep(sleepTime);
                                    else
                                        Thread.sleep(10);

                                } catch (Exception e) {};
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                    buttonPressed = false;
                    return true;
            }
        }
        else if (upSpace.contains((int) event.getX(), (int) event.getY())) {
            buttonPressed = true;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    new Thread(new Runnable() {
                        public void run() {
                            long ticksPS = 1000 / GameLoopThread.FPS;
                            long startTime;
                            long sleepTime;

                            while(buttonPressed) {
                                startTime = System.currentTimeMillis();

                                player.moveUp();
                                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                                try {
                                    if (sleepTime > 0)
                                        Thread.sleep(sleepTime);
                                    else
                                        Thread.sleep(10);

                                } catch (Exception e) {};
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                    buttonPressed = false;
                    return true;
            }

        }
        else if (downSpace.contains((int) event.getX(), (int) event.getY())) {
            buttonPressed = true;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    new Thread(new Runnable() {
                        public void run() {
                            long ticksPS = 1000 / GameLoopThread.FPS;
                            long startTime;
                            long sleepTime;

                            while(buttonPressed) {
                                startTime = System.currentTimeMillis();

                                player.moveDown();
                                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                                try {
                                    if (sleepTime > 0)
                                        Thread.sleep(sleepTime);
                                    else
                                        Thread.sleep(10);

                                } catch (Exception e) {};
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                    buttonPressed = false;
                    return true;
            }

        }
        else if (attackSpace.contains((int) event.getX(), (int) event.getY())) {
            Log.d("button", "attack!!");
        }
        else if(quitSpace.contains((int) event.getX(), (int) event.getY())) {
            Intent intent = new Intent(this.getContext(), StartMenu.class);
            getContext().startActivity(intent);
            this.destroyDrawingCache();
        }
        return true;
    }



}
