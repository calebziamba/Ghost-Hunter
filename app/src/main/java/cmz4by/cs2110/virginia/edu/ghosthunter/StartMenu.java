package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

public class StartMenu extends ActionBarActivity  {
    private Button mPlayButton;

    private Switch mSound;
    private static final String TAG = "StartMenu"; // used for Log messages

    private MediaPlayer mySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySound = MediaPlayer.create(this, R.raw.graveyard);
        mySound.start();
        mySound.setLooping(true);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "calling onCreate() in StartMenu");
        // Get the view ID of the play button and set an onClick listener
        // Tell the app what to do when Play Button Clicked
        setContentView(R.layout.activity_start_menu);
        mPlayButton = (Button) findViewById(R.id.play);

        // Using an anonymous class:
        mPlayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "calling onClick() on Play button");

                Intent intent = new Intent(StartMenu.this, MainActivity.class);
                StartMenu.this.startActivity(intent);
            }
        });

    }


    public void toggleMusic(View view) {
        mSound = (Switch) view;
        boolean on = mSound.isChecked();
        if (on) {
            mySound.start();
            mySound.setLooping(true);
        }
        else {
            mySound.pause();
        }
    }

}
