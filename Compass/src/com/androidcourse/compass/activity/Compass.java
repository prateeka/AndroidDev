/*
 * Description: This activity captures the sensor data using the software sensor
 * TYPE_ORIENTATION. The sensor data is accepted at the rate SENSOR_DELAY_NORMAL
 * and displayed using view SampleView.
 */

package com.androidcourse.compass.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;

public class Compass extends Activity {
	
	private static final String TAG = "Compass";
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private SampleView mView;
	private float[] mValues;
	
	private final SensorEventListener mListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (Config.DEBUG) {
				Log.d(TAG,
						"sensorChanged (" + event.values[0] + ", "
								+ event.values[1] + ", " + event.values[2]
								+ ")");
			}
			mValues = event.values;
			if (mView != null) {
				mView.invalidate();
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mView = new SampleView(this);
		setContentView(mView);
	}
	
	@Override
	protected void onResume()
	{
		if (Config.DEBUG) {
			Log.d(TAG, "onResume");
		}
		super.onResume();
		
		mSensorManager.registerListener(mListener, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onStop()
	{
		if (Config.DEBUG) {
			Log.d(TAG, "onStop");
		}
		mSensorManager.unregisterListener(mListener);
		super.onStop();
	}
	
	private class SampleView extends View {
		private final Paint mPaint = new Paint();
		private final Path mPath = new Path();
		
		public SampleView(Context context) {
			super(context);
			
			// Construct a wedge-shaped path
			mPath.moveTo(0, -50);
			mPath.lineTo(-20, 60);
			mPath.lineTo(0, 50);
			mPath.lineTo(20, 60);
			mPath.close();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			Paint paint = mPaint;
			
			canvas.drawColor(Color.WHITE);
			
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.FILL);
			
			int w = canvas.getWidth();
			int h = canvas.getHeight();
			int cx = w / 2;
			int cy = h / 2;
			
			canvas.translate(cx, cy);
			if (mValues != null) {
				canvas.rotate(-mValues[0]);
			}
			canvas.drawPath(mPath, mPaint);
		}
		
	}
}
