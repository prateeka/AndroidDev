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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidcourse.client.weather.processor.WeatherDataManager;
import com.androidcourse.client.weather.processor.WeatherDays;
import com.arya.androidcourse.service.http.IHttpService;

public class WeatherActivity extends Activity {
	
	private final String TAG = "WeatherActivity";
	private IHttpService httpService = null;
	private EditText zipCodeView;
	private Button goButton;
	private WeatherDataProviderRetrievor weatherDataProviderRetrievor;
	private Boolean[] weathersDisplayed;
	
	private DownloadWeatherTask[] weatherTasks;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		
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
	
	protected void initViews() {
		zipCodeView = (EditText) findViewById(R.id.zipCodeText);
		goButton = (Button) findViewById(R.id.goButton);
		weathersDisplayed = new Boolean[WeatherDays.values().length];
		resetWeathersDisplayed();
	}
	
	protected void resetWeathersDisplayed() {
		for (int idx = 0; idx < weathersDisplayed.length; idx++) {
			weathersDisplayed[idx] = false;
		}
	}
	
	private void bindService() {
		Log.i(TAG, "calling bindService");
		bindService(new Intent(IHttpService.class
				.getName()),
				serConn, Context.BIND_AUTO_CREATE);
		Log.i(TAG, "bind service returning");
	}
	
	protected void downloadWeatherData(String zipCode) {
		enableZipCodeView(false);
		WeatherDataManager weatherDataManager = getWeatherDataProvider(zipCode);
		weatherTasks = new DownloadWeatherTask[WeatherDays.values().length];
		
		for (int i = 0; i < weatherTasks.length; i++) {
			weatherTasks[i] = new DownloadWeatherTask(
					this,
					WeatherDays.values()[i],
					i, weatherDataManager);
			weatherTasks[i].execute();
		}
	}
	
	private WeatherDataManager getWeatherDataProvider(String zipCode) {
		weatherDataProviderRetrievor = WeatherDataProviderRetrievor
				.getInstance();
		return weatherDataProviderRetrievor.getWeatherDataProvider(
				httpService,
				zipCode);
	}
	
	private void downloadWeatherData() {
		final String DEFAULT_ZIP_CODE = "98105";
		downloadWeatherData(DEFAULT_ZIP_CODE);
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
			weatherDataProviderRetrievor.shutDownWeatherDataProvider();
		}
		catch (InterruptedException e) {
			Log.e(TAG, "InterruptedException encountered: " + e);
		}
		super.onDestroy();
	}
	
	public void onClickGetWeather(View view) {
		String zipCode = zipCodeView.getText().toString();
		Log.d(TAG, "Button clicked with zipCode " + zipCode);
		downloadWeatherData(zipCode);
	}
	
	void checkAndEnableZipCodeView(Integer downloadWeatherTaskID) {
		boolean flag = true;
		weathersDisplayed[downloadWeatherTaskID] = true;
		
		for (Boolean weatherDisplayed : weathersDisplayed) {
			if (!weatherDisplayed) {
				flag = false;
				break;
			}
		}
		if (flag) {
			enableZipCodeView(true);
			resetWeathersDisplayed();
		}
	}
	
	protected void enableZipCodeView(boolean flag) {
		zipCodeView.setEnabled(flag);
		goButton.setEnabled(flag);
	}
	
}