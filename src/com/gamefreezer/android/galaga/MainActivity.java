package com.gamefreezer.android.galaga;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    public static DisplayMetrics metrics;
    private MySurfaceView mySurfaceView;
    private InputHandler inputHandler;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor orientation;
    private final String tag = "GALAGA";

    public MainActivity() {
	super();
	Log.i("GALAGA", "MainActivity(): constructor.");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	try {
	    super.onCreate(savedInstanceState);
	    // Tools.log("MainActivity(): constructor.");
	    int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
		    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	    getWindow().addFlags(flags);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    Log.i(tag, "metrics.width: " + metrics.widthPixels
		    + " metrics.height: " + metrics.heightPixels);

	    mySurfaceView = new MySurfaceView(this);
	    setContentView(mySurfaceView);
	    inputHandler = new InputHandler(mySurfaceView.getGame());

	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	    List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
	    for (Sensor sensor : sensors) {
		Log.d(tag, "name: " + sensor.getName() + " type: "
			+ sensor.getType());
	    }

	    accelerometer = sensorManager
		    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    orientation = sensorManager
		    .getDefaultSensor(Sensor.TYPE_ORIENTATION);

	} catch (Exception e) {
	    Log.e(tag, "We have an exception: " + e);
	    String msg = e.getClass().getName() + " " + e.getMessage();
	    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
		    .show();
	}
    }

    @Override
    protected void onResume() {
	super.onResume();
	sensorManager.registerListener(this, accelerometer,
		SensorManager.SENSOR_DELAY_GAME);
	sensorManager.registerListener(this, orientation,
		SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
	sensorManager.unregisterListener(this);
	super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	return inputHandler.handleTouchEvent(event);
    }

    public void onSensorChanged(SensorEvent event) {
	inputHandler.handleTiltEvent(event);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}