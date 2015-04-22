package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Caleb on 4/19/2015.
 */
public class Surface extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Player player;

    public Surface(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        gameLoopThread = new GameLoopThread(this);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.poliwag_front);
        holder = getHolder();
        holder.addCallback(this);
        player = new Player(this, bmp);
    }


    public void draw(Canvas c) {
        c.drawColor(Color.BLACK);
        player.draw(c);
    }

    // SurfaceHolder.Callback methods
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

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

    public Player getPlayer() {
        return player;
    }

}
