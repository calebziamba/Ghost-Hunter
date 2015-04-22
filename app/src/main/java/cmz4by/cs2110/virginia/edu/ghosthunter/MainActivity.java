package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


public class MainActivity extends ActionBarActivity  {
    public int weapon;
    public String direction;
    private ViewSwitcher switcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_main);



        // Create a GLSurfaceView instance and
        // set as the ContentView for Activity
        //mGLView = new MyGLSurfaceView(this);

        //Start Menu will replace activity_main as opening screen




       // ImageView ghostIcon = (ImageView) findViewById(R.id.ghost);

       // ghostIcon.setImageResource(R.drawable.chillghost); //uncomment when image for ghost added



        }


    /*public void startGame(View view)  {

       // Button startButton = (Button) this.findViewById(R.id.startButton);
        //create instance of map

       // Intent intent;
       // intent = new Intent(MainActivity.this, startGame();)
       // startGame(View view).this.startActivity(intent);
        MapFragment gameScreen = new MapFragment();

       // setContentView(LayoutInflater.from(this).inflate(R.layout.mapscreen, null));


        ImageView ghostIcon = (ImageView) findViewById(R.id.ghost);
        ImageView playerIcon = (ImageView) findViewById(R.id.player);
        ghostIcon.setImageResource(R.drawable.chillghost); //uncomment when image for ghost added

        ghostIcon.setX(50);
        ghostIcon.setY(50);
        playerIcon.setX(0);
        playerIcon.setY(1000);
    }*/



    // Here begins the button responses. Each will probably be replaced by a separate method
    // for example: TurnUP would just call player.moveUp()
    public void beginGame(View view) {
        Intent intent = new Intent(MainActivity.this, GamePlay.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();

    }

    public void turnUp(View view) {
        // First, create view object to be able to call on specific view
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "up";

        // sets the player view object to the appropriate image
        if(weapon == 1) image.setImageResource(R.drawable.poliwag_back_spear); // if it has a spear
        else image.setImageResource(R.drawable.poliwag_back); // no spear

        // sets the Y-coordinate of the image view to the param.
        image.setY(0);
    }

    public void turnLeft(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "left";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_left_spear);
        else image.setImageResource(R.drawable.poliwag_left);

        // sets the X-coordinate of the image view to the param.
        image.setX(0);
    }

    public void turnRight(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "right";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_right_spear);
        else image.setImageResource(R.drawable.poliwag_right);

        image.setX(1000);
    }

    public void turnDown(View view) {
        ImageView image = (ImageView) findViewById(R.id.player);
        direction = "down";

        if(weapon == 1) image.setImageResource(R.drawable.poliwag_front_spear);
        else image.setImageResource(R.drawable.poliwag_front);

        image.setY(1000);

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
