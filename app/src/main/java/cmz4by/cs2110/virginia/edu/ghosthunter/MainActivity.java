package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;



public class MainActivity extends ActionBarActivity {
    public int weapon;
    public String direction;

  //  private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and
        // set as the ContentView for Activity
        //mGLView = new MyGLSurfaceView(this);
        setContentView(R.layout.activity_main);
    }

    class MyGLSurfaceView extends GLSurfaceView {
       // private final MyGLSurfaceView mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES2.0 context
            setEGLContextClientVersion(2);
            //mRenderer = new MyGLRenderer();

           // setRenderer(mRenderer);

            // Render the view only when there is a
            // change in the drawing data
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            }
    }


    public void turnUp(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "up";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_back_spear);
        else image.setImageResource(R.drawable.poliwag_back);
    }

    public void turnLeft(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "left";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_left_spear);
        else image.setImageResource(R.drawable.poliwag_left);
    }

    public void turnRight(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "right";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_right_spear);
        else image.setImageResource(R.drawable.poliwag_right);
    }

    public void turnDown(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "down";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_front_spear);
        else image.setImageResource(R.drawable.poliwag_front);

    }

    public void giveSpear(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        weapon = 1;
        if(direction.equals("up")) image.setImageResource(R.drawable.poliwag_back_spear);
        else if(direction.equals("left")) image.setImageResource(R.drawable.poliwag_left_spear);
        else if(direction.equals("right")) image.setImageResource(R.drawable.poliwag_right_spear);
        else if(direction.equals("down")) image.setImageResource(R.drawable.poliwag_front_spear);
        else image.setImageResource(R.drawable.poliwag_back_spear);
    }

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
