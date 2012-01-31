package com.androidcourse.client.weather.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.androidcourse.client.weather.processor.WeatherDays;
import com.arya.androidcourse.service.http.IHttpService;

public class WeatherActivity extends Activity {
	
	private final String TAG = "WeatherActivity";
	private IHttpService httpService = null;
	
	private DownloadWeatherTask[] weatherTasks;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if ((weatherTasks = (DownloadWeatherTask[]) getLastNonConfigurationInstance()) != null) {
			for (DownloadWeatherTask weatherTask : weatherTasks) {
				weatherTask.setContext(this);  // Give my AsyncTask the new
												// Activity reference
				if (weatherTask.getStatus() == AsyncTask.Status.FINISHED) {
					weatherTask.displayWeatherData();
				}
			}
		} else {
			bindService();
		}
	}
	
	private void bindService() {
		Log.i(TAG, "calling bindService");
		bindService(new Intent(IHttpService.class
				.getName()),
				serConn, Context.BIND_AUTO_CREATE);
		Log.i(TAG, "bind service returning");
	}
	
	protected void downloadWeatherData() {
		weatherTasks = new DownloadWeatherTask[WeatherDays.values().length];
		
		for (int i = 0; i < weatherTasks.length; i++) {
			weatherTasks[i] = new DownloadWeatherTask(
					this,
					httpService,
					WeatherDays.values()[i],
					i,
					98105);
			weatherTasks[i].execute();
		}
	}
	
	private final ServiceConnection serConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			Log.v(TAG, "onServiceConnected() called");
			httpService = IHttpService.Stub.asInterface(service);
			downloadWeatherData();
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected() called");
			httpService = null;
		}
	};
	
	// This gets called before onDestroy(). We want to pass forward a reference
	// to our AsyncTask.
	@Override
	public Object onRetainNonConfigurationInstance() {
		return weatherTasks;
	}
	
	@Override
	protected void onDestroy() {
		try {
			for (DownloadWeatherTask weatherTask : weatherTasks) {
				weatherTask.shutdown();
			}
		}
		catch (InterruptedException e) {
			Log.e(TAG, "InterruptedException encountered: " + e);
		}
		super.onDestroy();
	}
	
}