package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity  {
    public int weapon;
    public String direction;
    private Player player;
    private GameView gameView;
    private boolean buttonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        player = gameView.getPlayer();

    }


    /*
    // button listener for press and hold
    private View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent e) {

            // Start when button is first pressed
            if(e.getAction() == MotionEvent.ACTION_DOWN) {
                switch (v.getId()) {
                    case R.id.right:
                        buttonPressed = true;

                        new Thread(new Runnable() {
                            public void run() {
                                while(buttonPressed) {
                                    Log.d("button", "right button pressed");
                                    player.moveRight();
                                    try {
                                        Thread.sleep(500, 0);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                    case R.id.up:
                        // player.moveUp();
                        break;
                    case R.id.left:
                        // player.moveLeft();
                        break;
                    case R.id.down:
                        // player.moveDown();
                        break;
                    default:
                        break;
                }
            } else if(e.getAction() == MotionEvent.ACTION_UP) { // when button is released
                switch (v.getId()) {
                    case R.id.right:
                        // stop player.moveRight();
                        break;

                    case R.id.up:
                        // stop player.moveUp();
                        break;

                    case R.id.left:
                        Log.d("button", "left button released");
                        buttonPressed = false;
                        return true;

                    case R.id.down:
                        // player.moveDown();
                        break;

                    default:
                        break;
                }
            }
            return false;
        }
    };
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

