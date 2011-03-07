package com.gamefreezer.android.galaga;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static DisplayMetrics metrics;

    public MainActivity() {
	super();
	Log.i("GALAGA", "MainActivity(): constructor.");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	try {
	    super.onCreate(savedInstanceState);
	    // Game.log("MainActivity(): constructor.");
	    int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
		    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	    getWindow().addFlags(flags);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    Log.i("GALAGA", "metrics.width: " + metrics.widthPixels
		    + " metrics.height: " + metrics.heightPixels);

	    setContentView(new MySurfaceView(this));

	} catch (Exception e) {
	    Log.e("GALAGA", "We have an exception: " + e);
	    String msg = e.getClass().getName() + " " + e.getMessage();
	    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
		    .show();
	}
    }
}