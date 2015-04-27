
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback  {
    private Bitmap bmp;
    private GameLoopThread gameLoopThread;
    private List<Ghost> ghosts = new ArrayList<>();
    private Player player;

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int ammo = 6;

    private int x = 0;
    private ArrayList<Wall> walls;

    private ArrayList<Collectible> collectibles = new ArrayList<>();

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

    int score = 0;
    private Context context;

    private boolean buttonPressed = false;
    boolean spawnGhost = false;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_sprite_sheet);
        player = new Player(this, bmp);

        arrowDown = BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow);
        arrowUp = BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow);
        arrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
        arrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
        attackButton = BitmapFactory.decodeResource(getResources(), R.drawable.attack_button);

        //button spaces (each arrow is 100x100; the quit and pause buttons are 150x150
        quitSpace = new Rect(20, 926, 170, 1080);

    }
//max X is 1080, max Y is 1535

    public int getScore() {
        return this.score;
    }

    public void increaseScore(int sc){
        score = sc;
    }
    @Override
    public void draw(Canvas c) {

        Paint myPaint = new Paint();
        drawBackground(c, myPaint);

        myPaint.setColor(Color.RED);
        myPaint.setTextSize(80);
        c.drawText("Score: " + score, 50, 200, myPaint);
        myPaint.setTextSize(50);
        c.drawText("Ammo: " + ammo, 50, 280, myPaint);

        for (Wall wall: walls) {
            wall.draw(c, myPaint);
        }

        for(int i = projectiles.size() - 1; i > 0; i--) {
            Projectile p = projectiles.get(i);
            if (p.getX() > this.getWidth() || p.getX() < 0 - p.getBmp().getWidth()) {
                projectiles.remove(p);
            } else if (p.getY() > this.getHeight() || p.getY() < 0 - p.getBmp().getHeight()){
                projectiles.remove(p);
            }
            p.draw(c);
        }

        // spaces for buttons
        c.drawBitmap(arrowRight, this.getWidth()/2 + attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - arrowRight.getHeight(), myPaint);
        c.drawBitmap(arrowDown, this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight(), myPaint);
        c.drawBitmap(arrowLeft, this.getWidth()/2 - attackButton.getWidth()/2 - arrowRight.getWidth(), this.getHeight() - arrowDown.getHeight() - arrowLeft.getHeight(), myPaint);
        c.drawBitmap(arrowUp, this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - attackButton.getHeight() - arrowUp.getHeight(), myPaint);
        c.drawBitmap(attackButton, this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - attackButton.getHeight(), myPaint);

        c.drawRect(quitSpace, myPaint);

        player.draw(c);
        spawnGun(c);

        for (int i = collectibles.size() - 1; i > 0; i--) {
            Collectible collectible = collectibles.get(i);
            collectible.draw(c);
            if (Rect.intersects(player.getHitbox(), collectible.getHitbox())) {
                collectibles.remove(collectible);
                ammo += 5;
            }
        }

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
            for (int j = projectiles.size() - 1; j > 0; j--) {
                Projectile p = projectiles.get(j);
                if (Rect.intersects(p.getHitbox(), ghost.getHitboxFront()) ||
                        Rect.intersects(p.getHitbox(), ghost.getHitboxBack())) {
                    projectiles.remove(p);
                    ghosts.remove(ghost);
                }
            }
            ghost.draw(c);
        }

    }

    private void drawBackground(Canvas c, Paint myPaint) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        c.drawBitmap(bmp, 0, 0, myPaint);
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

        walls = Wall.createWalls(this);
        downSpace = new Rect(this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight(),
                                this.getWidth()/2 + attackButton.getWidth()/2, this.getHeight());
        leftSpace = new Rect(this.getWidth()/2 - attackButton.getWidth()/2 - arrowLeft.getWidth(), this.getHeight() - arrowDown.getHeight() - arrowLeft.getHeight(),
                                this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight());
        rightSpace = new Rect(this.getWidth()/2 + attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - arrowRight.getHeight(),
                                this.getWidth()/2 + attackButton.getWidth()/2 + arrowRight.getWidth(), this.getHeight() - arrowDown.getHeight());
        upSpace = new Rect(this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - attackButton.getHeight() - arrowUp.getHeight(),
                                this.getWidth()/2 + attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - attackButton.getHeight());
        attackSpace = new Rect(this.getWidth()/2 - attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight() - attackButton.getHeight(),
                                this.getWidth()/2 + attackButton.getWidth()/2, this.getHeight() - arrowDown.getHeight());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void spawnGun(Canvas c) {
        double rng = Math.random();
        if (rng < 0.005) collectibles.add(new Collectible(BitmapFactory.decodeResource(getResources(), R.drawable.gun),
                                            (int)(Math.random() * this.getWidth()), (int)(Math.random()*getHeight())));
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
        myPaint.setColor(Color.BLACK);
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
        if(event.getAction() == MotionEvent.ACTION_UP) {
            buttonPressed = false;
            return true;
        }
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
        else if (attackSpace.contains((int) event.getX(), (int) event.getY()) && this.ammo > 0 && event.getAction() == MotionEvent.ACTION_DOWN) {
            ammo -= 1;
            if (player.getDirection() == 0) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_down);
            } else if (player.getDirection() == 1) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_left);
            } else if (player.getDirection() == 2) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_right);
            } else if (player.getDirection() == 3) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_up);
            }
            projectiles.add(new Projectile(bmp, player));
            return true;
        }
        else if(quitSpace.contains((int) event.getX(), (int) event.getY())) {
            Intent intent = new Intent(this.getContext(), StartMenu.class);
            getContext().startActivity(intent);
            gameLoopThread.setRunning(false);
            this.destroyDrawingCache();
        }
        return true;
    }


}
