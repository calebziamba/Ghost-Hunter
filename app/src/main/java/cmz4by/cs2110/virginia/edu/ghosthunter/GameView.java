
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
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
    private Rect beginRoadblock;
    private Rect stopRoadblock;
    private Path playerPath;

    //button spaces
    private Rect quitSpace;
    private Rect pauseSpace;
    private Rect upSpace;
    private Rect downSpace;
    private Rect rightSpace;
    private Rect leftSpace;

    private Bitmap arrowUp;
    private Bitmap arrowDown;
    private Bitmap arrowRight;
    private Bitmap arrowLeft;
    private Bitmap quitGame;
    private Bitmap pauseGame;

    long score = 0;
    private Context context;

    private boolean buttonPressed = false;
    boolean spawnGhost = false;
    private boolean drawingRoadblock = false;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(1080,1920);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_sprite_sheet);
        player = new Player(this, bmp);

        arrowUp = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_back_spear);
        arrowDown = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_front_spear);
        arrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_left_spear);
        arrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_right_spear);
        quitGame = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_back_gun);
        pauseGame = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_right_gun);
        wall1 = new Rect(800, 1000, 820, 1300);
        wall2 = new Rect(0, 1300, 820, 1320);
        wall3 = new Rect(200, 800, 620, 820);
        wall4 = new Rect(350, 1010, 370, 1160);
        wall5 = new Rect(275, 200, 295, 500);
        wall6 = new Rect(660, 540, 1080, 560);
        wall7 = new Rect(750, 150, 770, 350);
        //button spaces (each arrow is 100x100; the quit and pause buttons are 150x150
        quitSpace = new Rect(20, 1700, 170, 1850);
        pauseSpace = new Rect(910, 1700, 1060, 1750);
        downSpace = new Rect (540, 1750 , 640, 1850);
        upSpace = new Rect (540, 1630, 640, 1730);
        rightSpace = new Rect (660, 1690 , 760, 1790);
        leftSpace = new Rect(420, 1690, 520, 1790);
        //weapon buttons
        beginRoadblock = new Rect(200,1400,300,1500);
        stopRoadblock = new Rect(340,1400,440,1500);




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

        Paint roadblockPaint = new Paint();
        roadblockPaint.setColor(Color.BLUE);

        myPaint.setTextSize(80);
        c.drawText("Score: " + score, 20, 100, myPaint);


        //walls
        c.drawRect(wall1, myPaint);
        c.drawRect(wall2, myPaint);
        c.drawRect(wall3, myPaint);
        c.drawRect(wall4, myPaint);
        c.drawRect(wall5, myPaint);
        c.drawRect(wall6, myPaint);
        c.drawRect(wall7, myPaint);

        // spaces for buttons
        c.drawRect(quitSpace, myPaint);
        c.drawRect(pauseSpace, myPaint);
        c.drawRect(upSpace, myPaint);
        c.drawRect(downSpace, myPaint);
        c.drawRect(rightSpace, myPaint);
        c.drawRect(leftSpace, myPaint);

        //draw weapon buttons
        c.drawRect(beginRoadblock,myPaint);
        c.drawRect(stopRoadblock,myPaint);



        // use these in place of quite, pause, and arrowSpaces
        /* canvas.drawBitmap(quitGame, 50, 1400, null);
        canvas.drawBitmap(pauseGame, 1050, 1900, null);
        canvas.drawBitmap(arrowUp, 580, 1700, null);
        canvas.drawBitmap(arrowDown, 580, 1800, null);
        canvas.drawBitmap(arrowLeft, 550, 1750, null);
        canvas.drawBitmap(arrowRight, 610, 1750, null); */

        while(drawingRoadblock){
            playerPath = new Path();
            playerPath.rLineTo(player.getPlayerX(), player.getPlayerY());
            c.drawPath(playerPath,roadblockPaint);
        }
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
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public Player getPlayer() {
        return player;
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
        if(touchedInsideItem(beginRoadblock, event.getX(),event.getY())){
           switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    drawingRoadblock = true;
            }
        }

        if(touchedInsideItem(stopRoadblock,event.getX(), event.getY())){
            drawingRoadblock = false;
        }

        if(touchedInsideItem(rightSpace, event.getX(), event.getY())) {
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
        else if (touchedInsideItem(leftSpace, event.getX(), event.getY())) {
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
        else if (touchedInsideItem(upSpace, event.getX(), event.getY())) {
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
        else if (touchedInsideItem(downSpace, event.getX(), event.getY())) {
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
        else if(touchedInsideItem(quitSpace, event.getX(), event.getY())) {
            Intent intent = new Intent(this.getContext(), StartMenu.class);
            getContext().startActivity(intent);
            this.destroyDrawingCache();
        }
        return true;
    }

    public boolean touchedInsideItem (Rect object, float touchX, float touchY) {
        return object.contains((int) touchX, (int) touchY);
    }

}
