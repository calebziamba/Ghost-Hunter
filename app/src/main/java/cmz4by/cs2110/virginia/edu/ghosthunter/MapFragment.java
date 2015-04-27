package cmz4by.cs2110.virginia.edu.ghosthunter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Student on 4/13/2015.
 */
public class MapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mapscreen, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FRAGMENT", "onCreate");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("FRAGMENT", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d("FRAGMENT", "onAttach");
        super.onAttach(activity);
    }

    public static void onCreate() {

    }

    public static void onAttach() {
    }
}
