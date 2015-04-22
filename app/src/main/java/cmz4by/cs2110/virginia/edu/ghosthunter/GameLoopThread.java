package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
    private GameView view;
    private boolean running = false;
    static final long FPS = 10;

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        int counter = 0;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            long newScore = startTime % 1000;
            view.increaseScore(newScore);

            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.draw(c);
                    if (counter < 10) counter++;
                    if (counter == 10) view.increaseScore(view.getScore() + 1);
                    counter = 0;

                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
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
