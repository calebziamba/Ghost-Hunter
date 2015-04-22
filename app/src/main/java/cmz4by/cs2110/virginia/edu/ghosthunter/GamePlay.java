package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Student on 4/14/2015.
 */
public class GamePlay extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));

    }
}
