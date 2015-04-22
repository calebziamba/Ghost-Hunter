package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Canvas;

/**
 * Created by Caleb on 4/19/2015.
 */
public class GameLoopThread extends Thread{
    private Surface surface;
    private boolean running = false;
    static final long FPS = 10;

    public GameLoopThread(Surface s) {
        this.surface = s;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while(running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();

            try {
                c = surface.getHolder().lockCanvas();
                synchronized (surface.getHolder()) {
                    surface.draw(c);
                }
            } finally {
                if (c != null) {
                    surface.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}
