package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Student on 4/13/2015.
 */
public class GameView extends SurfaceView {

    private final SurfaceHolder holder;
    private Bitmap ghost;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private Rect wall1;
    private Rect wall2;
    private Rect wall3;
    private Rect wall4;
    private Rect wall5;
    private Rect wall6;
    private Rect wall7;

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

    public GameView(Context context){
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

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
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
            }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
            }

        });
        ghost = BitmapFactory.decodeResource(getResources(), R.drawable.chillghost);
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
        quitSpace = new Rect(20, 1530, 170, 1680);
        pauseSpace = new Rect(910, 1530, 1060, 1680);
        downSpace = new Rect (540, 1580 , 640, 1680 );
        upSpace = new Rect (540, 1460, 640, 1560);
        rightSpace = new Rect (660, 1520 , 760, 1620);
        leftSpace = new Rect(430, 1520, 530, 1620);


    }




//max X is 1080, max Y is 1535

    int score = 0;
    @Override

    public void draw(Canvas canvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.BLACK);
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(ghost, x, 10, null);
        canvas.drawText("" + score, 100, 100, myPaint);

        //walls
        canvas.drawRect(wall1, myPaint);
        canvas.drawRect(wall2, myPaint);
        canvas.drawRect(wall3, myPaint);
        canvas.drawRect(wall4, myPaint);
        canvas.drawRect(wall5, myPaint);
        canvas.drawRect(wall6, myPaint);
        canvas.drawRect(wall7, myPaint);

       // spaces for buttons
        canvas.drawRect(quitSpace, myPaint);
        canvas.drawRect(pauseSpace, myPaint);
        canvas.drawRect(upSpace, myPaint);
        canvas.drawRect(downSpace, myPaint);
        canvas.drawRect(rightSpace, myPaint);
        canvas.drawRect(leftSpace, myPaint);



        // use these in place of quite, pause, and arrowSpaces
        /* canvas.drawBitmap(quitGame, 50, 1400, null);
        canvas.drawBitmap(pauseGame, 1050, 1900, null);
        canvas.drawBitmap(arrowUp, 580, 1700, null);
        canvas.drawBitmap(arrowDown, 580, 1800, null);
        canvas.drawBitmap(arrowLeft, 550, 1750, null);
        canvas.drawBitmap(arrowRight, 610, 1750, null); */



    }

}
