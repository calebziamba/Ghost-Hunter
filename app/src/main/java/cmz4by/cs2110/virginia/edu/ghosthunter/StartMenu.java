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
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

public class StartMenu extends ActionBarActivity  {
    private Button mPlayButton;
    private Button mViewScoreButton;
    private Button mSettingsButton;
    private Button mSetBackButton;
    private Button mHSBackButton;
    private ToggleButton mSound;
    private static final String TAG = "StartMenu"; // used for Log messages

    private MediaPlayer mySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_start_menu);
        Log.i(TAG, "calling onCreate() in StartMenu");
        // Get the view ID of the play button and set an onClick listener
        // Tell the app what to do when Play Button Clicked
        mPlayButton = (Button) findViewById(R.id.play);
        mySound = MediaPlayer.create(this, R.raw.graveyard);
        mySound.start();
        mySound.setLooping(true);

        // Get the view ID of the view highscore and set an onClick listener
        mViewScoreButton = (Button) findViewById(R.id.viewhighscore);

        mSettingsButton = (Button) findViewById(R.id.settings);
        // Using an anonymous class:
        mPlayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "calling onClick() on Play button");

                Intent intent = new Intent(StartMenu.this, MainActivity.class);
                StartMenu.this.startActivity(intent);
                StartMenu.this.finish();
            }
        });
        mViewScoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.high_score);
                Log.i(TAG, "calling onClick() on HighScore button");
                int a = 1;
                goBack(a);
            }

        });
        mSettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.settings);
                Log.i(TAG, "calling onClick() on Settings button");
                int a = 2;
                goBack(a);
                onMusicClicked();
                onHardClicked();
            }
        });
    }

    protected void goBack(int a) {
        if (a==2 ) {
            mSetBackButton = (Button) findViewById(R.id.settings_back);
            mSetBackButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onStart();
                    Log.i(TAG, "calling onClick() on Back button");

                }
            });
        }
        else if(a==1) {
            mHSBackButton = (Button) findViewById(R.id.hs_back);
            mHSBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                    Log.i(TAG, "calling onClick() on Back button");

                }
            });

        }

    }

    protected void onMusicClicked() {
        mySound = MediaPlayer.create(this, R.raw.graveyard);
        mSound = (ToggleButton) findViewById(R.id.soundButton);
        boolean on = mSound.isChecked();
        if (on && !mySound.isPlaying()) {
            mySound.start();
            mySound.setLooping(true);
        }
        else {
            mySound.pause();
        }
    }

    protected int onHardClicked() {
        ToggleButton hard = (ToggleButton) findViewById(R.id.hardButton);
        boolean on = hard.isChecked();
        if (on) {
            return 0;
        }
        else {
            return 1;
        }
    }


}
