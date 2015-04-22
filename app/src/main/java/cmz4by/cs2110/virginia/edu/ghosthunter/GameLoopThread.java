package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Canvas;

/**
 * Created by Student on 4/20/2015.
 */

    public class GameLoopThread extends Thread {

        private GameView view;

        private boolean running = false;



        public GameLoopThread(GameView view) {

            this.view = view;

        }



        public void setRunning(boolean run) {

            running = run;

        }



        @Override

        public void run() {

            while (running) {

                Canvas c = null;

                try {

                    c = view.getHolder().lockCanvas();

                    synchronized (view.getHolder()) {

                        view.draw(c);

                    }

                } finally {

                    if (c != null) {

                        view.getHolder().unlockCanvasAndPost(c);

                    }

                }

            }

        }

    }



